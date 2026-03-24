#or just run these in the terminal to run tests
javac -cp "test-lib/*" src/main/*.java src/test/*.java
java -jar test-lib/junit-platform-console-standalone-*.jar --class-path src --scan-class-path