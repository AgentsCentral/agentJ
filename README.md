# agentJ - Java Multi Agent Development Kit


AgentJ - Java Multi Agent Development Kit

## JAVA VERSION 
JDK 21+ is required

## Getting started (Preview version)

### Setup OpenAI Key as environment variable
This is required for unit tests. 

`export OPEN_AI_KEY=<YOUR_KEY>`

You can skip tests to avoid it by using `-DskipTests`.

Build and install agentJ in your local maven repo

`$./mvnw clean install`

or skip tests

`$./mvnw clean install-DskipTests`


## Setting up a standalone weather agentic

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
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>1.7.21</version>
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

Example local maven repo path  `~/.m2/repository`


### Create a stub weatherAPI
Weather API to be used by LLM agentic to retrieve current weather information
```
public class WeatherAPI {

    private final Random temperatureGenerator = new Random();
    private final ObjectMapper mapper = new ObjectMapper();

    public String getWeatherInformation(String city){
        double temperature = temperatureGenerator.nextDouble(1D, 50);
        return asJson(new WeatherInfo(city, temperature + " Celsius"));
    }

    private String asJson(Object o){
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
```
### Create Weather Agent System

```
public class WeatherSystem {


    //Configure the OpenAIConfig(temperature, API_KEY). Make sure the temperature is supported by the model 
    private static final OpenAIConfig config = new OpenAIConfig(1D, System.getenv("OPEN_AI_KEY"));

   //Setup the tool bag of the weather agentic
    private static final ToolBag weatherTools = new ToolBag() {
        private final WeatherAPI api = new WeatherAPI();

        @Tool(name = "weather_tool", description = "Provides weather information about the city")
        public String weatherInformation(@ToolParam(name = "city", description = "City") String city) {

            return api.getWeatherInformation(city);
        }
    };

    //Initialize the weather agentic
    private static final SimpleAgent weatherAgent = new SimpleAgent("weather_agent", //name 
            new Model("o4-mini", config), //model with OpenAIConfig
            List.of(stringInstructor("You are a weather assistant. You are responsible for telling current weather information about the city.")), //instructions
            List.of(weatherTools), //tools
            List.of() //handoffs
    );

    //Initialize agentic system
    public static Team getTeam(){
        return new Team("Weather Agent System", weatherAgent, List.of());
    }

}

```
### Running the standalone agentic using HTTP

Setup the `StandaloneWeatherAgent` to run it using `JettyHttpRunner` . Default port is `8181`

```
public class StandaloneWeatherAgent {


    public static void main(String[] args) throws Exception {

        final HttpConfig weatherChatConfig = new HttpConfig("/chat/*", WeatherSystem.getTeam());
        final AgentJConfig agentJConfig = new AgentJConfig(List.of(weatherChatConfig));

        AgentJStarter.run(new JettyHttpRunner(defaultJettyConfig(), agentJConfig));
    }


}

```

### Health Checks

#### Health check

`curl --location 'http://localhost:8181/health'`

#### Liveness check

`curl --location 'http://localhost:8181/health/liveness'`

#### Readiness check

`curl --location 'http://localhost:8181/health/readiness'`

### Chatting with application

*Request*

```
curl --location 'http://localhost:8181/chat/cv_f66cc4ab8c6346f6bfdd898c255dcd2c' \
--header 'Content-Type: application/json' \
--data '{
    "message": "How is the weather in paris"
}'
```

*Response*

```
{
    "conversationId": "cv_f66cc4ab8c6346f6bfdd898c255dcd2c",
    "messages": [
        "The current temperature in Paris is approximately 46.3 Â°C."
    ]
}
```


## Full code examples are available on

Code Examples can be found here
https://github.com/AgentsCentral/agentj-examples



