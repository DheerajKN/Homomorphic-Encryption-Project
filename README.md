# Homomorphic-Encryption-Project
Contains the project code to develop a Homomorphic Encryption using Databases developed in Java and swings developed on Paillier Cryptosystem


Once the server machine is installed with MySQL, create a Username with which the client will be accessing the system. We need to assign a password to each of the clients who will be using the database. Assign proper password to the user, and mention from which all IP that he can connect to it. Command wise the steps are: 
CREATE USER 'Username'@'IP' IDENTIFIED BY 'PASSWORD' 
GRANT ALL ON database.tablename TO 'Username' @'IP' IDENTIFIED BY 'PASSWORD' 
This process is done to show how the system is behaving under real world scenarios. It depicts the actual implementation of the software. Whether the data that we are sending is going completely encrypted or not. Man in the Middle attacks can also be done to make sure that the data is safe. Also to ensure client to client Encryption is ensured. 


Rest of the Application requires simple Java API and Jar file of mysql attached in the git and swings extension present in the Eclipse Java IDE Software
