# "Customer Database Management"

## Description:
A Java application for managing customers and appointments using a SQL database.

The main goal of this project is to demonstrate SQL database manipulation using a Java application with user-friendly GUI. Extra functionality is added to improve the versatility of the application. 

**Features:**
* the ability to add/modify/delete customer and appointment data. 
* supplemental information like names, dates, contacts, etc. associated with each customer/appointment. 
* tables reporting database information about customers and appointments. 
* a login screen. 

## How to Run:
1. Download all project files. 
2. Download IntelliJ Community Edition from [here](https://www.jetbrains.com/idea/download). 
3. Download JDK 11 from [here](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html). 
4. Download JavaFX SDK 11.0.2 [here](https://gluonhq.com/products/javafx). (click "include older versions" checkbox). 
5. In IntelliJ:
	1. Add a new Path Variable with name "PATH_TO_FX" and value as the *"javafx-sdk-11.0.2/lib"* folder filepath
	2. In Project Structure, add the following two libraries:
		1. the javafx-sdk-11.0.2/lib folder
		2. the [mysql-connector-java-8.0.22.jar file](/lib/mysql-connector-java-8.0.22.jar) in the project files
	3. In the Main.java configurations, set the VM Options to "--module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics"

MySQL Database Setup:
	A. If you already have MySQL installed:
		1. Add a new DBA user with these credentials: user="sqlUser", pass"Catcher54#", limit to hosts matching="localhost"
		2. Create a new connection and set connection name="CustomerDBClient", hostname="localhost", user="sqlUser"
		3. Create a new schema called "client_schedule"
		4. Run the SQL queries in the "/lib/sql_ddl_query_1.txt" file 
		5. Afterwards, run the SQL queries in the "/lib/sql_dml_query_2.txt" file
	B. If you don't have MYSQL installed:
		1. Download & Run MySQL Installer 8 (dev.mysql.com/downloads/windows/installer/8.0.html)
		2. Choose custom setup
		3. Install MySQL server and MySQL workbench
		4. Create a password for the root account
		5. Finish installation
		6. Open MySQL Workbench
		7. Click on "Local instance MySQL80" box
		8. enter root password from earlier
		9. On left-side of window, click on Administration tab in the Navigator window.
		10. Click on "Users and Privileges"
		11. Click bottom-left "Add Account" button.
		12. make user="sqlUser", pass="Catcher54#", and limit to hosts matching="localhost"
		13. Go to "Administrative Roles" tab and select the "DBA" box
		14. Click "Apply" button
		15. Go back to the home tab and click the "+" button next to "MySQL Connections"
		16. Set connection name="CustomerDBClient", hostname="localhost", user="sqlUser"
		17. Click "Test Connection" to check connection
		18. Click Ok button
		19. Click on the newly created CustomerDBClient box and input "Catcher54#" password
		20. In the top-left third row, click the 4th icon with the popup text "Create a new schema"
		21. set name to "client_schedule" and click Apply and Finish
		22. Copy and paste text from "/lib/sql_ddl_query_1.txt" file into "Query 1" tab and pres the first lightning bolt icon
		23. Delete text and then copy and paste text from "/lib/sql_dml_query_2.txt" file into the same tab and press the same icon. 
		24. Your database should be setup and will run in the background. 

Run [Main.java](/src/mainApplication/Main.java) IntelliJ

## How to Use:
1. On the login screen, use "test" for both user and pass.
2. Use the dropdown boxes in the menu to quickly filter database information. 
3. Click the "Customers" or "Appointment" button at the top-left to view charts for all database information. 
4. Use the "Add", "Modify", or "Delete" buttons to alter database information.

## Extra Details:
* The MySQL Connector Driver 8.0.22 version is used specifically for its automatic timezone conversion. 

## Author:
Austin Kim
