[![Build Status](https://travis-ci.org/dat3startcode/rest-jpa-devops-startcode.svg?branch=master)](https://travis-ci.org/dat3startcode/rest-jpa-devops-startcode)

### Preconditions
*In order to use this code, you should have a local developer setup + a "matching" droplet on Digital Ocean* 
# Getting Started
Clone this project

Delete the .git folder and Do "Your own" git init

Create your OWN repository for this project on github

Commit and Push your code to this repository

Go to Travis-ci.com and Sign up with GitHub

Accept the Authorization of Travis CI. You’ll be redirected to GitHub

Click the green Activate button, and select the the new repository to be used with Travis CI

Create two local databases (on your vagrant image) named exactly (exactly is only for this proof of concept) as below:

startcode
startcode_test
Create a REMOTE database on your Droplet named exacly like this: startcode

in a terminal (git bash for Windows Users) in the root of the project type: mvn test

Hopefully the previous step was a success, if not, fix the problem(s)
### Fetching data from servers
The template for fetching data from multiple servers is created in the ServerFacade class. In this example, data is only fetched from a single API, and this should eventually be changed by adding a list of different hosts and looping through this instead.
### Deploying to droplet
Remember to change the <remote.server> property in pom.xml to match the IP/domain name of your droplet. Also, don't forget to create environment variables on Travis CI. 
### JWT signature
A fixed secret key is displayed in plain text in security.SharedSecret. This should be removed, and eventually be read as an environment variable on the remote production server.



