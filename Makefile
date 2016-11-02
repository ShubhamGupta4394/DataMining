#
# Compiling Java App
#
sourcefiles = \
App.java \
TestDataReader.java \
TestSolver.java
 
classfiles  = $(sourcefiles:.java=.class)
 
all: $(classfiles)

%.class: %.java
	javac -d . -classpath . $<
	
clean:
	rm -f *.class