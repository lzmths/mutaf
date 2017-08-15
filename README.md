# CPPMT
A tool about Mutation Testing in CPP (C preprocessor). <br>
This tools implements some mutation operations from Mutation Operators for Preprocessor-Based Variability (Mustafa Al-Hajjaji et al). <br>
<br>
<br>
You need Java 7 (or higher), Python 3 and srcml (www.srcml.org) <br>
<br>
You can call CPPMT like this: <br>
./cppmt -mutations mutations-names -file path-to-file <br>
e.g: <br>
./cppmt -mutations MCIB AFIC -file /tmp/input.c
