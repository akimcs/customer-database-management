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
	1. Create a Path Variable with name=`PATH_TO_FX` and value=JavaFX lib folder. 
	2. Add these two libraries in the Project Structure settings: (1) JavaFX lib folder (2) [mysql-connector-java-8.0.22.jar](/lib/mysql-connector-java-8.0.22.jar) file. 
	3. Set the VM Options in [Main.java](/src/mainApplication/Main.java) configurations to `--module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics`. 
6. MySQL Database Setup:
	1. If MySQL is installed/setup:
		* Create a new DBA user: user=`sqlUser`, pass=`Catcher54#`, limit to hosts matching=`localhost`.
		* Create a new connection: name=`CustomerDBClient`, hostname=`localhost`, user=`sqlUser`.
		* Create a new schema: name=`client_schedule`.
		* Copy the SQL queries in [sql_ddl_query_1.txt](/lib/sql_ddl_query_1.txt) and run in a query tab. 
		* Copy the SQL queries in [sql_dml_query_2.txt](/lib/sql_dml_query_2.txt) and run in a query tab. 
	2. If MySQL is NOT installed: 

		1. Installation/Setup: 
			* download MySQL Installer 8 from [here](dev.mysql.com/downloads/windows/installer/8.0.html).
			* choose *"Custom Setup"* -> install *"MySQL Server"* and *"MySQL Workbench"* -> create a root password
			* finish installation
		2. Setup DBA User: 
			* open *"MySQL Workbench"*
			* click *"Local Instance MySQL80"* box -> enter root password from earlier
			* click *"Navigator"* window -> *"Administration"* tab (at bottom) -> click *"Users and Privileges"* -> *"Add Account"*
			* input user=`sqlUser`, pass=`Catcher54#`, and limit to hosts matching=`localhost`
			* click "Administrative Roles" tab -> select *"DBA"*
			* click "Apply"
		3. Setup Database Server: 
			* click *"Home"* icon -> click *"+"* button (next to *"MySQL Connections"*)
			* input name=`CustomerDBClient`, hostname=`localhost`, user=`sqlUser`
			* click *"Ok"*
		4. Populate Database: 
			* Click *"CustomerDBClient"* box; pass=`Catcher54#`
			* Click *"Create a new schema"* icon (top-left of screen, 3rd row, 4th icon)
			* input name=`client_schedule`; 
			* click *"Apply"*; click *"Finish"*
			* Copy and paste text from "/lib/sql_ddl_query_1.txt" file into "Query 1" tab and pres the first lightning bolt icon
			* Delete text and then copy and paste text from "/lib/sql_dml_query_2.txt" file into the same tab and press the same icon
			* Your database should be setup and will run in the background
7. Run [Main.java](/src/mainApplication/Main.java) in IntelliJ. 

## How to Use:
1. On the login screen, use "test" for both user and pass.
2. Use the dropdown boxes in the menu to quickly filter database information. 
3. Click the "Customers" or "Appointment" button at the top-left to view charts for all database information. 
4. Use the "Add", "Modify", or "Delete" buttons to alter database information.

## Extra Details:
* The MySQL Connector Driver 8.0.22 version is used specifically for its automatic timezone conversion. 

## Author:
Austin Kim
