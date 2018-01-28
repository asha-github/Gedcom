Problem Solved : GEDCOM Parser Challenge

Reason for choosing this problem : 
	I took up Gedcom parser challenge as the description of the problem seemed to be very clear. Vehicle survey problem looked interesting but I had some open questions regarding it. As I don't like taking assumptions much and wanted to keep them minimal, I decided to select one of the remaining problems. Out of the remaining problems, I took up Gedcom paser challenge due to the ease in testing. Creation of test data seemed more difficult for 1800 problem compared to Gedcom one. 

Design : 
	1. Data reader will parse the data in the input file and fills up an intermediate data store implemented as tree. Data writer will read from intermediate data store and convert it to the required format. SRP is strongly followed.
	2. Data reader part and writer part has been designed to be loosely coupled. Intermediate data store is added so that writer and reader can be changed independently.
	3. Intermediate data store is designed to be a tree as it is the most suitable data structure to represent this hierarchical data. 
	4. Design is done in a flexible way that multiple writers and readers can be added based on different formats easily without touching the core files.

Code : 
	 Code is uploaded in gitHub. Code can be cloned from github with url https://github.com/ashasebastian/Gedcom.git. If code can't be cloned from github, import the Maven project from the attached Zip file.
	
Dependencies : 
	1. Requires java 8 runtime.
	2. Build tool used is maven.
	3. Depends on junit 4 for testing.
	
Execution :-
	Main class is com.aconex.gedcom.GedcomParserApplication. Input file path and output file path should be given as arguments to GedcomParserApplication. First argument is taken as input file and second as output file. If input file path is not provided during execution, default file sampleinput.txt under resources folder will be taken up for parsing. If output file path is not provided during execution, output file will be created as output.xml under "output" folder in project directory.
	
	Maven execution commands :-
	
		Execute the following from project directory :  
		1. mvn clean -> Cleans up project directory.
		2. mvn package -> Compiles and runs test cases.
		3. mvn exec:java -Dexec.mainClass="com.aconex.gedcom.GedcomParserApplication" -Dexec.args="/Users/ashas/Projects/Gedcom/GedcomParser/src/main/resources/sampleinput.txt /Users/ashas/Projects/Gedcom/GedcomParser/output/output.xml"  -> Executes parser application with arguments. It can be executed without input and output file arguments also in which case default ones will be taken.

	Code can be run from any IDE like Eclipse by importing the project to it and use Run as Java Application option on main class GedcomParserApplication with or without arguments as mentioned above.
	
Testcase Execution :-
	Test suite class com.aconex.gedcom.GedcomParserTestSuite is provided to run all test cases. All test cases can be invoked by running "mvn test" command also.

Assumptions Taken :
	1. If there is any error while parsing any line in the input file, I assume that the expected behaviour is to stop parsing rather than continuing with the next line. 
	2. I am assuming that the program is not expected to be run in an environment where memory is a big constraint. If it is so, intermediate data store creation can be avoided. I have kept the intermediate data store to keep the design flexible.
	
