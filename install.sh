#!/bin/bash

mvn install:install-file -Dfile=./lib/virtjdbc4_2.jar -DgroupId=virtjdbc -DartifactId=virtjdbc -Dversion=4.2 -Dpackaging=jar -DgeneratePom=true  
