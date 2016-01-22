Event producer example
--------
Tiny example how to send events to a message broker in a lightweight way.
Based on spring-boot and apache-camel.
 

build
-----

mvn install


test
----

Start it via the main-method in class SimpleApplication and 
send a json structure like:

	{
	   "id":"666",
	   "sureName":"Kilmister"
	}


to http://localhost:8080
