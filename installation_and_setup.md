# How to Install and Setup this Project

1. Download all project files. 

2. Download IntelliJ Community Edition from [here](https://www.jetbrains.com/idea/download).

3. Download JDK 11 from [here](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html). 

4. Download JavaFX SDK 11.0.2 [here](https://gluonhq.com/products/javafx). (click "include older versions" checkbox). 

5. In IntelliJ:
	1. Create a Path Variable with name=`PATH_TO_FX` and value=JavaFX lib folder. 

	2. Add these two libraries in the Project Structure settings: (1) JavaFX lib folder (2) [mysql-connector-java-8.0.22.jar](/lib/mysql-connector-java-8.0.22.jar) file. 

	3. Set the VM Options in [Main.java](/src/mainApplication/Main.java) configurations to `--module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics`. 

6. Set up the MySQL database using step 6A or 6B.

7. Run [Main.java](/src/mainApplication/Main.java) in IntelliJ. 

## 6A) If MySQL is installed/setup:

1. If MySQL is installed/setup:

	* create a new DBA user: user=`sqlUser`, pass=`Catcher54#`, limit to hosts matching=`localhost`.

	* create a new connection: name=`CustomerDBClient`, hostname=`localhost`, user=`sqlUser`.

	* create a new schema: name=`client_schedule`.

	* copy the SQL queries in [sql_ddl_query_1.txt](/lib/sql_ddl_query_1.txt) and run in a query tab. 

	* copy the SQL queries in [sql_dml_query_2.txt](/lib/sql_dml_query_2.txt) and run in a query tab. 

## 6B) If MySQL is NOT installed:

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
	* click *"CustomerDBClient"* box -> pass=`Catcher54#`

	* click *"Create a new schema"* icon (top-left of screen, 3rd row, 4th icon)

	* input name=`client_schedule`

	* click *"Apply"* -> click *"Finish"*

	* copy text from [sql_ddl_query_1.txt](/lib/sql_ddl_query_1.txt) into query tab and press lightning bolt icon

	* repeat above but replace text with [sql_ddl_query_1.txt](/lib/sql_dml_query_2.txt)

	* your database should be setup and will run in the background
