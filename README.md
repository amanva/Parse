# Parse

<h1>Overview</h1>

This Java program parses a file containing flow log data and maps each row to a tag based on a lookup table.

<h3>Assumptions</h3>

  - The program is designed to support the version 2 format.
  - The program only accounts for 3 numerical values which are "6" for TCP, "17" for UDP, and "1" for ICMP. Others will need to be added.
  - The user will change the text files manually within the code.
  - The user will be required to make sure that the format and files are correct.

<h3>Compilation</h3>

Download the zip file and extract the necessary files

To compile the program, go to the directory of the source code and run the following command:
javac Parser.java

To execute the program, use the following command:
java Parser

Ensure that the txt and csv files are in the same directory.

<h3>Test</h3>

The program only works if the format matches the default format being version 2.

Test Case:
  - The program has been tested with the sample cases.
  - The program has been tested with edge cases that will give different outputs.
  - The program has been tested with more than 10000 mappings in the lookup file.


