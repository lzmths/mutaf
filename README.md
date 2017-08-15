# CPPMT
A tool about Mutation Testing in CPP (C preprocessor). \n
This tools implements some mutation operations from Mutation Operators for Preprocessor-Based Variability (Mustafa Al-Hajjaji et al). \n
\n
\n
You need Java 7 (or higher), Python 3 and srcml (www.srcml.org) \n
\n
You can call CPPMT like this: \n
./cppmt -mutations mutations-names -file path-to-file \n
e.g: \n
./cppmt -mutations MCIB AFIC -file /tmp/input.c
