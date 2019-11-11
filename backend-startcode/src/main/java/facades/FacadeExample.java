package facades;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import entities.RenameMe;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class FacadeExample {

    private static FacadeExample instance;
    private static EntityManagerFactory emf;
    final static String[] URLS = {"https://jsonplaceholder.typicode.com/comments/","https://jsonplaceholder.typicode.com/posts/",
        "https://jsonplaceholder.typicode.com/albums/","https://jsonplaceholder.typicode.com/photos/","https://jsonplaceholder.typicode.com/todos/"};
    //Private Constructor to ensure Singleton
    private FacadeExample() {}
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static FacadeExample getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FacadeExample();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    //TODO Remove/Change this before use
    public long getRenameMeCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long renameMeCount = (long)em.createQuery("SELECT COUNT(r) FROM RenameMe r").getSingleResult();
            return renameMeCount;
        }finally{  
            em.close();
        }
    }
    
    private static String fetchFromServers(String URL, String arg) throws MalformedURLException, ProtocolException, IOException {
        URL url = new URL(URL + arg);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        String jsonStr = "";
        try (Scanner scan = new Scanner(con.getInputStream())) {
            while (scan.hasNext()) {
                jsonStr+=scan.nextLine();
            }
        }
        return jsonStr;
    }
    public void getDataFromServers() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Queue<Future> futureList = new LinkedList();
        List<String> allData = new ArrayList();
        for(String URL : URLS) {
            Future<String> future = executor.submit(()-> {
                return fetchFromServers(URL,"1");
            });
            futureList.add(future);
        }
        while(!futureList.isEmpty()) {
            Future<String> f = futureList.poll();
            if(f.isDone()) {
                allData.add(f.get());
            } else {
                futureList.add(f);
            }
        }
    }
}
