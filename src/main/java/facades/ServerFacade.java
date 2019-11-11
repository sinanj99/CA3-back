/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sinanjasar
 */
public class ServerFacade {

    private static ServerFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private ServerFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static ServerFacade getServerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ServerFacade();
        }
        return instance;
    }

    private static String fetchFromServer(String URL, String arg) throws MalformedURLException, ProtocolException, IOException {
        URL url = new URL(URL + arg);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        String jsonStr = "";
        try (Scanner scan = new Scanner(con.getInputStream())) {
            while (scan.hasNext()) {
                jsonStr += scan.nextLine();
            }
        }
        return jsonStr;
    }

    public List<String> fetchFromServers() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Queue<Future> futureList = new LinkedList();
        List<String> allData = new ArrayList();
        String[] URLS = {"https://jsonplaceholder.typicode.com/comments/", "https://jsonplaceholder.typicode.com/posts/",
            "https://jsonplaceholder.typicode.com/albums/", "https://jsonplaceholder.typicode.com/photos/", "https://jsonplaceholder.typicode.com/todos/"};
        for (String URL : URLS) {
            Future<String> future = executor.submit(() -> {
                return fetchFromServer(URL, "1");
            });
            futureList.add(future);
        }
        while (!futureList.isEmpty()) {
            Future<String> f = futureList.poll();
            if (f.isDone()) {
                try {
                    allData.add(f.get());
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                futureList.add(f);
            }
        }
        return allData;
    }
}
