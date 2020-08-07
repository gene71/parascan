parascan:
		javac -d ~/source/jprojects/parascan src/*.java
run:
		java -classpath ".:jars/sqlite-jdbc-3.30.1.jar" ParaMain "test.db" "/home/geo/source"
clean:
		rm *.class
		rm *.db
		rm *.jar
jar:
		jar cfm ParaJar.jar Manifest.txt *.class
runjar:
		java -jar ParaJar.jar "test.db"  "/home/geo/source"
