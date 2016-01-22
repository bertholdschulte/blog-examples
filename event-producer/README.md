template
--------
empty slim web project based on spring-boot

build
-----

mvn install

deploy
------


docker build -t spring-boot-web:0.0.1 .
docker run -d --name web -p 8080:8080 spring-boot-web:0.0.1

test
----

issue a request to _http://localhost:8080/check_
it should return the string _IMOK_

