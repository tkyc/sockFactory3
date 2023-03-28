

cd src/main/java
javac org/example/QuickAck.java 
javah -d native -cp . org.example.QuickAck

mv native/*.h ../../../native
cd ../../../native
#gcc -shared -o libquickack.so -fPIC quickack.c -I/usr/lib/jvm/java-8-openjdk-amd64/include/ -I/usr/lib/jvm/java-8-openjdk-amd64/include/linux
gcc -shared -o libquickack.so -fPIC quickack.c -I/usr/lib/jvm/java-11-openjdk-amd64/include/ -I/usr/lib/jvm/java-11-openjdk-amd64/include/linux

cd ..
mvn clean install ; cp mssql-jdbc-12.2.0.jre8.jar ./target/ ; java -cp "./target/*" org.example.Main