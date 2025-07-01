# agentJ - Java Multi Agent Development Kit


AgentJ - Java Multi Agent Development Kit

## JAVA VERSION 
JDK 21+ is required

## Getting started (Preview version)
Build and install agentJ in your local maven repo

`$ ./mvn clean install`


## Developing your own multi-agent system
Using maven

Add Dependencies

```
    <dependencies>
        <dependency>
            <groupId>ai.agentscentral</groupId>
            <artifactId>agentj-openai</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>ai.agentscentral</groupId>
            <artifactId>agentj-jetty</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
```



Add your local maven repo to the `pom.xml`


```
<repositories>
    <repository>
    <id>local-repo</id>
    <name>Local Repository</name>
    <url>YOUR_LOCAL_REP_PATH_HERE</url> 
    </repository>
</repositories>
```

`~/.m2/repository`





