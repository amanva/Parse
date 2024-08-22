# Parse

Overview

This Java program parses a file containing flow log data and maps each row to a tag based on a lookup table.

Assumptions
  - The program is designed to support the version 2 format
  - The program only accounts for 3 numerical values which are "6" for TCP, "17" for UDP, and "1" for ICMP. Others will need to be added.

Compilation
To compile the program, go to the directory of the source code and run the following command:
javac Parser.java

To execute the program, use the following command:
java Parser

Ensure that the txt and csv files are in the same directory
