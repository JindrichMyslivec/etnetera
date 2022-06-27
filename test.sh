#!/bin/bash

#curl -X DELETE localhost:8080/frameworks

#curl localhost:8080/frameworks

#curl -X POST localhost:8080/frameworks -H "Content-Type: application/json"  -d '{ "name": "asd" }'

curl -X PUT localhost:8080/frameworks/1 -H "Content-Type: application/json"  -d '{ "name": "SpringBoot", "version": "1.0" }'

curl localhost:8080/frameworks

#curl -X DELETE localhost:8080/frameworks/3

