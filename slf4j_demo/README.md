# Slf4j

- Simple Logging Facade for Java
- Its a facade for various logging frameworks (log4j, logback, java.util.logging)
- Code uses Slf4j, and during runtime it picks real logger via dependency

# Quick Steps

1. Add dependencies
   1. slf4j-api
   2. logback-classic
2. Create logback.xml
3. Import and use Logger, LoggerFactory.getLogger(Main.class) or @Slf4j from lombok
4. Log with log levels

# Dependency

- Slf4j Api

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.13</version>
</dependency>
```

- This is only api (interface)
- Defines Logger, LoggerFactory, etc

- Logback classic (Implementation)

```xml
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.4.14</version>
</dependency>
```

- Logging backend - implementation
- Writes logs

# Demo

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyClass {
    // Create logger (static final is the convention)
    private static final Logger logger = LoggerFactory.getLogger(MyClass.class);

    public void doSomething() {
        logger.info("This is an info log!");
        logger.debug("Debug info: value={}", 42); // {} for variables!
        logger.warn("Something might be wrong!");
        logger.error("An error occurred!", new Exception("Oops"));
    }
}
```

- With lombok

```java
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyClass {
    public void test() {
        log.info("Hello from Lombok SLF4J!");
    }
}
```

```java
// BAD:
logger.debug("User id: " + userId + " logged in");

// GOOD:
logger.debug("User id: {} logged in", userId);
```

# Logging Levels

- Most severe to least severe
  - error
  - warn
  - info
  - debug
  - trace

# Customization

- In spring boot customize logs in **application.properties**

```
logging.level.root=INFO
logging.level.com.yourcompany=DEBUG
```

- In plain java projects use logback.xml in src/main/resources

```xml
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    <root level="info">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```

- Log to file

```xml
<configuration>
    <!-- Console appender: logs to console -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- File appender: logs to a file -->
    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <file>logs/app.log</file>   <!-- relative to your project root -->
        <append>true</append>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- Root logger: send logs to both console and file -->
    <root level="info">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="FILE" />
    </root>
</configuration>

```

- Rolling file appender

```xml
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} -%msg%n</pattern>
        </encoder>
    </appender>

    <appender name="ROLLING" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/app.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>logs/app-%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxFileSize>10MB</maxFileSize>
            <maxHistory>7</maxHistory>
            <totalSizeCap>1GB</totalSizeCap>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="info">
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="ROLLING"/>
    </root>
</configuration>
```

- Skeleton of logback.xml
  - configuration
    - appender
      - encoder
        - pattern
      - file
      - append
      - rollingPolicy
        - fileNamePattern
        - maxFileSize
        - maxHistory
        - totalSizeCap
    - root
      - appender-ref

# Appenders
