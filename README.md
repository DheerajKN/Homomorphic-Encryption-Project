# Homomorphic-Encryption-Project
Contains the project code to develop a Homomorphic Encryption using Databases developed in Java and swings developed on Paillier Cryptosystem

## Contents
- [Introduction to the Project](#introduction)
- [Installation](#installation-procedure)
- [Requirements](#other-Requirements)
- [Contributors](#contributors)
### Introduction
> With the Advent of Internet, Data has become freely available for anyone to use it carefully and also in wronger sense. So there is a need to implement newer Encryption methods that provides better control to those who need it at the most and lesser those who need it less. There comes the methodology of Homomorphic Encryption.
    --K. N. Dheeraj, Jayakrishnan Ashok and Chaitanya Subhedar
    
> **Homomorphic Encryption** is an Encryption methodology that makes allows modification to be done the data without actually seeing the kind of values they are dealing with.

Once the server machine is installed with MySQL, create a Username with which the client will be accessing the system. We need to assign a password to each of the clients who will be using the database. Assign proper password to the user, and mention from which all IP that he can connect to it. Command wise the steps are installation procedure:

### Installation procedure
* Installation of JRE and JDK and installing Eclipse
* Using the Mysql Connector Jar file that links the DB and Java Program
* Inside the Mysql terminal write the below command
```
CREATE USER 'Username'@'IP' IDENTIFIED BY 'PASSWORD' 
```
This helps in Connecting the db with system to get remote access of with another machine and then type below command
```bash
GRANT ALL ON database.tablename TO 'Username' @'IP' IDENTIFIED BY 'PASSWORD' 
```

#### Then run the main uisql file and program should run fine

This process is done to show how the system is behaving under real world scenarios. It depicts the actual implementation of the software. Whether the data that we are sending is going completely encrypted or not. Man in the Middle attacks can also be done to make sure that the data is safe. Also to ensure client to client Encryption is ensured. 

### Other Requirements
Rest of the Application requires simple Java API and Jar file of mysql attached in the git and swings extension present in the Eclipse Java IDE Software

## Notable International Publications
- Successfully Published an online journal at **International Journal of Innovative Technology and Exploring Engineering(TM) IJITEE**   
    - Link for the same - https://www.ijitee.org/wp-content/uploads/papers/v8i8/G5854058719.pdf
- **ISSN:** 2278-3075 and **Retrieval Number:** G5854058719

## Important Note
Also the user would include the Git link used and Contributor's name in their work.

### Contributors
<pre>
  - K. N. Dheeraj        (<a href="kndheeraj0@gmail.com">kndheeraj0@gmail.com</a>)
  - Chaintanya Subhedar  (<a href="csubhedar1996@gmail.com">csubhedar1996@gmail.com</a>)
  - Jayakrishnan Ashok   (<a href="jayakrishnanashok@gmail.com">jayakrishnanashok@gmail.com</a>)
 </pre>
