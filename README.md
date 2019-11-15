[![Build Status](https://travis-ci.org/dat3startcode/rest-jpa-devops-startcode.svg?branch=master)](https://travis-ci.org/dat3startcode/rest-jpa-devops-startcode)

### Preconditions
*In order to use this code, you should have a local developer setup + a "matching" droplet on Digital Ocean* 
# Getting Started
### Fetching data from servers
The template for fetching data from multiple servers is created in the ServerFacade class. In this example, data is only fetched from a single API, and this should eventually be changed by adding a list of different hosts and looping through this instead.
### Deploying to droplet
Remember to change the <remote.server> property in pom.xml to match the IP/domain name of your droplet. Also, don't forget to create environment variables on Travis CI. 
### JWT signature
A fixed secret key is displayed in plain text in security.SharedSecret. This should be removed, and eventually be read as an environment variable on the remote production server.




