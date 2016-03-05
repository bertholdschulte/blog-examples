Event producer code-sketch
--------
Tiny code-sketch how to send events to a message broker in a lightweight way.
Based on spring-boot and apache-camel. Additionally I used [camel-spring-boot](http://camel.apache.org/spring-boot.html), which keeps the camel configuration quite simple, almost zero, in this sketch.
 

build
-----

mvn install


test
----

Start it via the main-method in class SimpleApplication and 
send a json structure like:

	{
	   "id":"666",
	   "sureName":"Kilmister",
	   "invoiceRequested":"true"
	}


to http://localhost:8080. You will receive a message in your event-channel. To play with it if no message broker is available just replace the endpoint kafka://... with another one like file://... (in this case a file containing the message is created per event)

