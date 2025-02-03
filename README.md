# APITestingUsingRestAssured
Test rest API using Rest Assured library in Java

## Run testng suite
    mvn clean test -Dsurefire.suiteXmlFiles=testng.xml

## Runs only available testng suite file

    mvn clean test

## Run tests filtered by groups

    mvn test -Dgroups=group1

## Install this version of json server version which allows you to have integer id's

npm install -g json-server@1.0.0-beta.3