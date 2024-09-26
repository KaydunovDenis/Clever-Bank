### Installation for Mac
1. **Install Homebrew** (for installing other programs):

   ```bash
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   ```

2. **Install Maven** using Homebrew:

   ```bash
   brew install maven
   ```

3. **Check an installation**:

   ```bash
   mvn -v
   ```


### Run

1. Install docker (https://docs.docker.com/engine/install/)
2. Run docker app locally.
2. Run [docker-compose.yaml](docker%2Fdocker-compose.yaml)
3. Run [CleverBank.java](src%2Fmain%2Fjava%2Fcom%2Fgithub%2Fkaydunov%2FCleverBank.java)

**PowerMockito**  
In Java, it is not possible to directly dump a constructor using standard Mockito or other popular libraries such as
JUnit. However, for such cases you can use the PowerMockito library, which extends the capabilities of Mockito and
allows to dunk the designer’s call.

```
<dependency>
    <groupId>org.powermock</groupId>
    <artifactId>powermock-api-mockito2</artifactId>
    <version>2.0.9</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.powermock</groupId>
    <artifactId>powermock-module-junit5</artifactId>
    <version>2.0.9</version>
    <scope>test</scope>
</dependency>
```


### Links
https://www.baeldung.com/intro-to-powermock  
https://www.baeldung.com/java-mockito-constructors-unit-testing  



