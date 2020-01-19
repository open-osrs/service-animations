# service-animations
Works as a database you can read/write NPC animations to
  
# Setup  
  
Import the project using gradle.  
After gradle finishes init:  
  
Use the gradle run task  
OR  
simply launch service.SpringBootWebApplication  
OR  
use the shadowJar gradle task, and you can use build/libs/-all jar to launch using the java -jar command, no tomcat.  
  
The following endpoints exist:  
http://localhost:8081/animations/submit : Submits an animation to the database  
http://localhost:8081/animations/get : Returns all currently collected keys  
