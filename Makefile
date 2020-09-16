parascan:
		javac -d ~/source/jprojects/parascan src/*.java
run:
		java -classpath ".:res/sqlite-jdbc-3.30.1.jar" ParaMain "test.db" "/home/geo/source"
clean:
		rm -r parascan10
		rm *.jar
		rm *.db		
jar:
		jar cfm ParaJar.jar Manifest.txt parascan10/*.class
runjar:
		java -jar ParaJar.jar "test.db"  "/home/geo/source"
