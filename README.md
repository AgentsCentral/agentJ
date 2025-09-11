# agentJ - Java Multi Agent Development Kit

AgentJ - Java Multi Agent Development Kit

## JAVA VERSION 
JDK 21+ is required

## Getting Started

### Add Dependencies

```xml
<dependencies>
    <dependency>
        <groupId>ai.agentscentral</groupId>
        <artifactId>agentj-openai</artifactId>
        <version>0.0.4</version>
    </dependency>
    <!-- Add agentj-jetty only if you need HTTP server capabilities -->
    <dependency>
        <groupId>ai.agentscentral</groupId>
        <artifactId>agentj-jetty</artifactId>
        <version>0.0.4</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>1.7.21</version>
    </dependency>        
</dependencies>
```

## Setting up a Standalone Weather Agent

This example demonstrates how to create and deploy a simple weather agent in an HTTP server, configured with an AI agent and weather API.

### 1. Create a Weather API

First, create a simple weather API that your agent will use:

```java
public class WeatherAPI {
    private final Random temperatureGenerator = new Random();
    private final ObjectMapper mapper = new ObjectMapper();

    public String getWeatherInformation(String city) {
        double temperature = temperatureGenerator.nextDouble(1D, 50);
        return asJson(new WeatherInfo(city, temperature + " Celsius"));
    }

    private String asJson(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
```

### 2. Create the Weather Agent

Next, create your agent using the `Agent` class:

```java
public class WeatherAgent {
    private static final OpenAIConfig config = new OpenAIConfig(1D, System.getenv("OPEN_AI_KEY"));

    private static final ToolBag weatherTools = new ToolBag() {
        private final WeatherAPI api = new WeatherAPI();

        @Tool(name = "weather_tool", description = "Provides weather information about the city")
        public String weatherInformation(@ToolParam(name = "city", description = "City") String city) {
            System.out.println("Fetching weather for: " + city);
            return api.getWeatherInformation(city);
        }
    };

    private static final Agent weatherAgent = new Agent("weather_agent",
            new Model("o4-mini", config),
            List.of(stringInstructor("You are a helpful weather assistant. Provide current weather information when asked about cities.")),
            List.of(weatherTools),
            List.of()
    );

    public static Agent getAgent() {
        return weatherAgent;
    }
}
```

### 3. Run the Agent as an HTTP Server

#### 3.1 Add HTTP Server Dependencies

Make sure you have the `agentj-jetty` dependency in your `pom.xml` as shown in the dependencies section.

#### 3.2 Configure and Start the Server

```java
public class WeatherChatBot {
    public static void main(String[] args) throws Exception {
        // Configure your agent
        final HttpConfig weatherChatConfig = new HttpConfig("/chat/*", WeatherAgent.getAgent(), List.of(), null);
        final AgentJConfig agentJConfig = new AgentJConfig(List.of(weatherChatConfig));

        // Start the server with default configuration
        AgentJStarter.run(new JettyHttpRunner(JettyConfig.defaultConfig(), agentJConfig));
    }
}
```

#### 3.3 Server Configuration (Optional)

Customize the server configuration if needed:

```java
// Custom server configuration
JettyConfig jettyConfig = JettyConfig.builder()
    .port(8080)  // Change default port
    .maxVirtualThreads(200)
    .reservedThreads(2)
    .build();

// Start with custom config
AgentJStarter.run(new JettyHttpRunner(jettyConfig, agentJConfig));
```

#### 3.4 CORS Configuration (Optional)

```java
CORSConfig corsConfig = CORSConfig.builder()
        .allowedOrigins(Set.of("http://localhost:5173"))
        .allowedHeaders(Set.of("Content-Type", "Authorization"))
        .allowedMethods(Set.of("GET", "POST"))
        .allowCredentials(true)
        .build();

final HttpConfig weatherChatConfig = new HttpConfig("/chat/*", WeatherAgent.getAgent(), List.of(), corsConfig);
final AgentJConfig agentJConfig = new AgentJConfig(List.of(weatherChatConfig));
```

### 4 Test the API

Once running, you can test the API using curl:

```bash
# Health check
curl http://localhost:8181/health

# Chat with the agent
curl -X POST http://localhost:8181/chat/<session_id> \
  -H "Content-Type: application/json" \
  -d '{"message": "What\'s the weather like in New York?"}'
```

## Advanced Configuration

### CORS Configuration

When running behind a web interface, configure CORS:

```java
CORSConfig corsConfig = CORSConfig.builder()
        .allowedOrigins(Set.of("http://localhost:5173"))
        .allowedHeaders(Set.of("Content-Type", "Authorization"))
        .allowedMethods(Set.of("GET", "POST"))
        .allowCredentials(true)
        .build();
```

### Security Notes

1. Always specify exact origins in production
2. Only enable necessary HTTP methods and headers
3. Consider adding authentication for production use

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
    "sessionId": "cv_f66cc4ab8c6346f6bfdd898c255dcd2c",
    "messages": [
        "The current temperature in Paris is approximately 13.7°C."
    ]
}
```


## Full code examples are available on

Code Examples can be found here
https://github.com/AgentsCentral/agentj-examples





