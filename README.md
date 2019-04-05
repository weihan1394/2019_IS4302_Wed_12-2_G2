# FoodIE

IS4302 project: FOODIE

Team member: Hui Zhong, Yi Xuan, Wei Han
- Chia Wei Han (A0156099R)
- Lim Hui Zhong (A0161024X)
- Soong Yi Xuan (A0155973R)

## Documentation
#### Architecture Diagram
![foodie_ArchitectureDiagram.png](https://github.com/weihan1394/FoodIE/blob/master/documentation/ArchitectureDiagram.png)

#### Class Diagram
![foodie_ClassDiagram.png](https://github.com/weihan1394/FoodIE/blob/master/documentation/databaseDiagram.png)

## Installation
### 1. Hyperledger

- Import BNA file to hyper ledger playground (HyperLedger/is4302foodie-network.bna)
https://github.com/weihan1394/FoodIE/blob/master/hyperledger/is4302foodie-network.bna
- Testing data to create the particpate (HyperLedger/flowforHyperLedger.json)
https://github.com/weihan1394/FoodIE/blob/master/hyperledger/flowforHyperLedger.json
- Once deploy can created the particpate we than can do the transactions following the json file that is provided.
#### Start Playground
- npm run setup_crypto
- npm run start_fabric
- npm run start_playground
#### Stop Playground
- npm run stop_playground
- npm run stop_fabric
#### Clean Fabric
- npm run clean_fabric
#### Reset Docker
- docker system prune --all --force --volumes;
- npm run build image;

### 2. AngularJs
- Use the command "npm install" to install the required packages
- Edit "proxy-conf.json" file. Change the port number to the port number running on ExpressJs
- To run with proxy: "npm start"
- Go to localhost:4200

### 3. JavaEE and Web Application
- Setup with JWT security token
- Setup Netbeans
- Setup MySQL

#### Installation Required
No. | Category | Description | Version | Download 
--- | --- | --- | --- |--- 
1 | Java EE | Java Platform, Standard Edition (Java SE), Development Kit (JDK) | 8 Update 191 | Do NOT install Java SE 9, 10 or 11 as they are not compatible with GlassFish 4.1.1 and/or NetBeans 8.2 https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
2 | Java EE | NetBeans | 8.2 | https://netbeans.org/downloads/8.2 (All Bundle)
3 | Java EE | Java Platform, Enterprise Edition (Java EE) | 7 | #
4 | Java EE | GlassFish Server Open Source Edition | 4.1.1 (build 1) | #
5 | Version Control System | Git | Latest Version | Choose the required binary package here: http://git-scm.com/downloads
6 | Relational Database Management System | MySQL Community Server | 8.0.13 |	https://dev.mysql.com/downloads/mysql (For Microsoft Windows, you may use the all-in-one installer which will also install the Workbench)
7 | Relational Database Management System | MySQL Workbench (GUI Tool) | 8.0.13 | https://dev.mysql.com/downloads/workbench
8 | Relational Database Management System | MySQL Connector/J | 8.0.13 | https://dev.mysql.com/downloads/connector/j

#### Installation Steps
This installation guide assumes that you are using Microsoft Windows.
1. Install (1)
2. Create a new system environment variable JAVA_HOME and set the value to the installation path of your JDK, e.g., C:\Program Files\Java\jdk1.8.0_191.
3. Amend your system environment variable PATH by adding/appending %JAVA_HOME%\bin to it. Move this entry to the top of the path entries list.
4. Start command prompt and run the command java -version to verify that JDK has been installed successfully. The correct version that should be reported is 1.8.0_191.
5. Install (2), (3) and (4) with the bundled installer. 
6. Look for the installation path of your GlassFish, e.g., C:\glassfish-4.1.1\glassfish. Navigate to the config subfolder and open asenv.bat for editing. Append set AS_JAVA=<JDK_INSTALL_PATH>, e.g., set AS_JAVA=C:\Program Files\Java\jdk1.8.0_191, to the end of the file. Save and close the file.
7. Look for the installation path of your NetBeans, e.g., C:\Program Files\NetBeans 8.2. Navigate to the etc subfolder and open netbeans.conf for editing. Look for the line netbeans_jdkhome and remove the leading # character Configure netbeans_jdkhome to point to the installation path of your JDK 8. You need to enclose the path with a pair of double quotation marks. E.g., netbeans_jdkhome="C:\Program Files\Java\jdk1.8.0_191"
8. Create a new system environment variable GLASSFISH_HOME and set the value to the installation path of your GlassFish, e.g., C:\glassfish-4.1.1\glassfish.
9. Amend your system environment variable PATH by adding/appending %GLASSFISH_HOME%\bin to it. Move this entry to the top of the path entries list.
10. Start file explorer and navigate to the GlassFish installation path, e.g., C:\glassfish-4.1.1\glassfish. Goto into the modules subfolder and delete the JAR file org.eclipse.persistence.moxy.jar. Download the replacement JAR file [org.eclipse.persistence.moxy-2.6.1.ja] (https://mvnrepository.com/artifact/org.eclipse.persistence/org.eclipse.persistence.moxy/2.6.1-RC1). Place the replacement JAR file in the modules subfolder. It is not necessary to rename the replacement JAR file.
11. Return back to the parent GlassFish installation folder and then navigate to the domains\domain1\osgi-cache subfolder. Check whether there is a felix subfolder inside. If yes, delete the felix subfolder.
12. Start command prompt and run the command asadmin version to verify that GlassFish has been installed successfully. The correct version that should be reported is 4.1.1.
13. Run the command asadmin start-domain domain1 to start GlassFish. Open your web browser and navigate to http://localhost:8080 and http://localhost:4848 to ensure that GlassFish is working correctly.
14. Run the command asadmin stop-domain domain1 to stop GlassFish.
15. Install (6) and (7) using the MySQL Installer (all-in-one) and by selecting the Setup Type as Developer Default. There is no need to install (18) again separately using the Workbench installer if you are using the all-in-one installer for Microsoft Windows.
16. For Windows, after installing MySQL, you need to set the default time zone information in the MySQL configuration file, e.g., C:\ProgramData\MySQL\MySQL Server 8.0\my.ini. Open the configuration file for editing and look for the "[mysqld]" section. Immediately below this line, add in a new line "default-time-zone='+8:00'". Restart your MySQL server if it is currently running.
17. (19) does not require installation. Just extract and copy mysql-connector-java-8.0.13.jar into these two folders: 1) NetBeans installation folder - "C:\Program Files\NetBeans 8.2\ide\modules\ext"; and 2) GlassFish installation folder - "C:\glassfish-4.1.1\glassfish\domains\domain1\lib". Your installation folders might differ from mine. To use this new JAR file in NetBeans, replace the existing driver JAR file in NetBeans under "Services > Databases > Drivers > MySQL (Connector/J driver) > Customize".
18. With version 8.x of the MySQL Connector/J driver, you will not be able to use the MySQL Server Node utility tool in NetBeans 8.2. In addition, you need to revise the database URL format to "jdbc:mysql://localhost:3306/<DATABASE_NAME>?zeroDateTimeBehavior=CONVERT_TO_NULL". Note that the "convertToNull" option is now "CONVERT_TO_NULL".

### 4. ExpressJS
- Created certificate and key
- Setup with proxy to connect to (3)
- To run: node server.js 

#### Installation Required
No. | Category | Description | Version | Download 
--- | --- | --- | --- |--- 
1 | Javascript | NodeJS | 10.15.0 LTS | https://nodejs.org/en/download
2 | Javascript | npm | 	6.4.1 | npm is installed by default with NodeJS

#### Installation Steps
1. Install (1) by running the installer.
2. Amend your system environment variable PATH by adding/appending the NodeJS installation path, e.g., C:\nodejs, to it.
3. Start command prompt and run the command node --version to verify that NodeJS has been installed successfully. The correct version that should be reported is 10.15.0.
4. You do not need to install (10) separately since npm is installed by default with NodeJS.
5. Start command prompt and run the command npm --version to verify that npm has been installed successfully. The correct version that should be reported is 6.4.1.


### 5. Postman (Sample)
- [Join link](https://www.getpostman.com/collections/b18463fe71dc4cf34e7c)
- [http](http://localhost:8321/FoodIEBackend-war/ws/GenericResource/Login)
- [https](https://localhost:8323/foodie/FoodIEBackend-war/ws/GenericResource/Login)


### Task completion
~~~
Hyperledger
- [✓] Setup hyperledger
- [✓] Setup hyperledger API
- [✓] Pull transaction chain
- [✓] Setup scenario
- [✓] Test script
Frontend
- [✓] Setup angular front-end
- [✓] Review front-end (to use theme or Primefaces)
- [✓] Connect to Java Web Application
Backend
- [✓] Setup Mysql database
- [✓] JavaEE and Web Application
- [✓] Setup express-JS
- [✓] Setup reverse proxy
- [✓] Setup localhost HTTPS
- [✓] Setup POSTMAN testing environment
- [✓] Setup transaction
- [✓] Security
~~~
- [Trello](https://trello.com/b/bnIMZ5vF/foodie)

## License
[MIT](https://choosealicense.com/licenses/mit/)

## Edit
[ReadME](https://www.makeareadme.com/)
