Pre-requisites
---------------
1. 	Apache Maven should be installed on the target machine and environment variable M2_HOME should point to the root directory
	of Maven installation.
2.	Jdk 1.8 installed on the target machine and environment variable JAVA_HOME should point to the root directory
	of Jdk 1.8 installation.

Steps to build
----------------
1. 	Open command prompt (or Unix prompt) and go to the location where this file (readme.txt) is located. There should be a pom.xml
	under the same location.
2.	Type "mvn clean install" (without the quotes) and press Enter. This will compile and build the project as well as run the unit
	test cases that are located under src/test/java. You can write more test cases under src/test/java and have them executed in the 
	same way.
3.	Type "mvn assembly:single" (without the quotes) and press Enter. The command will compile all Java source files, and package 
	all necessary files inside an archive called: fruitmart-checkout-2.0-bin.zip. The archive will be created under the 'target' directory. 
	The archive contains the core application in the form of an executable Jar file.
	
Steps to run
----------------
1. 	Copy the archive (fruitmart-checkout-2.0-bin.zip) created from the build process to any location of your choice. For example: C:\temp
2. 	Now unzip the archive; this will create a folder called fruitmart-checkout-2.0 under C:\temp.
3. 	Go inside the fruitmart-checkout-2.0 folder. It should have the executable Jar file (fruitmart-checkout-2.0.jar), a copy of this README.txt, 
	and a lib folder containing dependencies (log4j-1.2.17.jar).
4. 	Open a command prompt and go to the location where the archive was unzipped (in this example, that is: C:\temp\fruitmart-checkout-2.0)
5.	To run the application, type the following command, then press Enter.
	java -jar fruitmart-checkout-2.0.jar <fruit-names in any order>
	For example, for 3 apples and 2 oranges, you may type:
	java -jar fruitmart-checkout-2.0.jar apple orange apple apple orange
	or
	java -jar fruitmart-checkout-2.0.jar orange apple apple apple orange
	etc.
6.	The application will print the final contents of the shopping cart (which may contain bonus fruits) gross price, total discount and net price.
7.	The default configuration (see src/main/resources/fruitmart.properties) specifies only apples and oranges as available fruits. To specify
	another fruit (e.g. mango, strawberry) and its price per unit, edit this file.
8.	Promotional offers can also be configured by editing src/main/resources/fruitmart.properties file.
9.	If src/main/resources/fruitmart.properties has been edited, then follow the instructions from "Steps to build" section to rebuild the project.
	Next time the executable jar file is run, the new configuration should take effect.
