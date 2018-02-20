GEDCOM PARSER REQUIREMENT :-

GEDCOM is the "GEnealogical Data COMmunication" file format. It is a plain-text electronic format used to transfer genealogical data.
The purpose of this challenge is to develop a simple parser that can convert a GEDCOM file to XML.
GEDCOM Format
The GEDCOM file format is very straightforward. Each line represents a node in a tree. It looks something like this:
  0 @I1@ INDI
  1 NAME Jamis Gordon /Buck/
  2 SURN Buck
  2 GIVN Jamis Gordon
  1SEXM
  ...
In general, each line is formatted thus:
  LEVEL TAG-OR-ID [DATA]
The LEVEL is an integer, representing the current depth in the tree. If subsequent lines have greater levels than the current node, they are children of the current node.
TAG-OR-ID is either a tag that identifies the type of data in that node, or it is a unique identifier. Tags are 3- or 4-letter words in uppercase. The unique identifiers are always text surrounded by "@" characters (i.e., "@I54@"). If an ID is given, the DATA is the type of the subtree that is identified.
So, to take the example given above apart:
1. "0@I1@INDI".This starts a new subtree of type INDI(individual).The id for this individual is "@I1@".
2. "1NAMEJamisGordon/Buck/".This starts a NAME subtree with a value of "Jamis Gordon /Buck/".
3. "2SURNBuck".This is a subelement of the NAME subtree, of type SURN ("surname").
4. "2GIVNJamisGordon".This is a subelement of the NAME subtree, of type GIVN (“given name”).
5. "1SEXM".CreatesanewsubelementoftheINDIelement,oftype"SEX"(i.e.,"gender").
Variable whitespace is allowed between the level and the tag. Blank lines are ignored.
 The challenge, then, is to create a parser that takes a GEDCOM file as input and converts it to XML. The snippet of GEDCOM given above would become:
<gedcom>
  <indi id="@I1@">
<name value="Jamis Gordon /Buck/">   <surn>Buck</surn>
  <givn>Jamis Gordon</givn> </name>
<sex>M</sex>
  </indi>
 </gedcom>
  
DESIGN : 
	1. Data reader will parse the data in the input file and fills up an intermediate data store implemented as tree. Data writer will read from intermediate data store and convert it to the required format. SRP is strongly followed.
	2. Data reader part and writer part has been designed to be loosely coupled. Intermediate data store is added so that writer and reader can be changed independently.
	3. Intermediate data store is designed to be a tree as it is the most suitable data structure to represent this hierarchical data. 
	4. Design is done in a flexible way that multiple writers and readers can be added based on different formats easily without touching the core files.
	
DEPENDENCIES : 
	1. Requires java 8 runtime.
	2. Build tool used is maven.
	3. Depends on junit 4 for testing.
	
EXECUTION :
	Main class is com.aconex.gedcom.GedcomParserApplication. Input file path and output file path should be given as arguments to GedcomParserApplication. First argument is taken as input file and second as output file. If input file path is not provided during execution, default file sampleinput.txt under resources folder will be taken up for parsing. If output file path is not provided during execution, output file will be created as gedcom.xml under "output" folder in project directory.
	Maven execution commands :-
		Execute the following from project directory :  
		1. mvn clean -> Cleans up project directory.
		2. mvn package -> Compiles and runs test cases.
		3. mvn exec:java -Dexec.mainClass="com.aconex.gedcom.GedcomParserApplication" -Dexec.args="<input file name> <output file name>"
		-> Executes parser application with arguments. It can be executed without input and output file arguments also in which case default ones will be taken.
Example : mvn exec:java -Dexec.mainClass="/Users/ashas/Projects/Gedcom/GedcomParser/src/main/resources/sampleinput.txt /Users/ashas/Projects/Gedcom/GedcomParser/output/gedcom.xml"  
	
TESTCASE EXECUTION :
	Test suite class com.aconex.gedcom.GedcomParserTestSuite is provided to run all test cases. All test cases can be invoked by running "mvn test" command also.

ASSUMPTIONS :
	1. If there is any error while parsing any line in the input file, I assume that the expected behaviour is to stop parsing rather than continuing with the next line. 
	2. I am assuming that the program is not expected to be run in an environment where memory is a big constraint. If it is so, intermediate data store creation can be avoided. I have kept the intermediate data store to keep the design flexible.
	3. Format is strictly followed as "LEVEL TAG-OR-ID [DATA]". In the sample input, entries like "1 FAMC @F0005@" is there. I am assuming "@F0005@" to be a DATA field rather than ID field eventhough it is surrounded by @ symbol as it does not come as second element.
