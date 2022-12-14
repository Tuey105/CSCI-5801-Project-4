# CSCI-5801-Project-4
## PPALMS
This system is created to generate parson problems out of source code files. This system can be compiled and executed in command line or IntelliJ, although unit tests needs Intellij to execute unit tests.

### To Compile and Execute in command line:
- Run "javac ppalms.java"
- Run "java ppalms"

### To Compile and Excute in Intellij:
- Open ppalms folder in Intellij
- Right click ppalms.java and select 'ppalms.main()'

### To Run Unit Tests in Intellij:
- Right click ppalmsTest.java and select 'run 'ppalmsTest'

## How To Use System
import a file of your choosing by clicking the import button. Then to annotate, select lines clicking and dragging over the lines. You can select multiple lines at once or separate lines to choose only those lines for generation. Or you can select no lines and generation will use all lines in source code file for generation. Comments will automatically be excluded from parson problem generation. Select type of parson problem to generate by clicking the problem type button and choosing the available options. Type number of parson problems you want to generate in the text field that says "Enter Amount" (Entering a large amount will timeout the system when generating parson problems). When complete, click generate to generate a parson problem file.
