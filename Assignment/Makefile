JAVAC=/usr/bin/javac
JAVA=/usr/bin/java
.SUFFIXES: .java .class

SRCDIR=src
BINDIR=bin

default:
	$(JAVAC) -sourcepath $(SRCDIR) -d $(BINDIR) $(SRCDIR)/**/*.java

make run:
	$(JAVA) -cp $(BINDIR)/ TreeGrow sample_input.txt
clean:
	rm $(BINDIR)/**/*.class

