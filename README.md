# agentJ - Java Multi Agent Development Kit


AgentJ - Java Multi Agent Development Kit

## JAVA VERSION 
JDK 21+ is required

## Getting started (Preview version)

### Setup OPENAI Key as environment variable
This is required for unit tests. You can skip tests to avoid it by using `-DskipTests`.
`export OPEN_AI_KEY=<YOUR_KEY>`

Build and install agentJ in your local maven repo

`$./mvnw clean install`

or skip tests

`$./mvnw clean install-DskipTests`


## Developing your own multi-agent system
Using maven

### Add Dependencies

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



### Configure local maven repo


```
<repositories>
    <repository>
    <id>local-repo</id>
    <name>Local Repository</name>
    <url>YOUR_LOCAL_REP_PATH_HERE</url> 
    </repository>
</repositories>
```

#### Example local maven repo path
`~/.m2/repository`


## Code Examples

Code Examples can be found here
https://github.com/AgentsCentral/agentj-examples



