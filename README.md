### Clever-Bank

## Project description
Clever-Bank is a console-based banking application designed to manage multiple banks, users, and accounts, facilitating secure transactions, interest calculations, and generating detailed receipts.   
It adheres to SOLID principles and ensures asynchronous operations in a multi-threaded environment, with a focus on code quality and maintainability.

### Main functions:

1. Deposit and withdrawal of funds.
2. Transfers between clients of Clever-Bank and clients of other banks.
3. Automatic interest charge on accounts at the end of the month.
4. Generation and saving of checks for each transaction.
5. Multithreading and deadlock protection.

## Technology stack

- Java 17
- Maven
- PostgreSQL
- JDBC
- Lombok
- Servlets 
- Docker

## Data structure

The application operates with the following entities:
- **Bank**: keeps accounts and users.
- **User**: owns one or more accounts.
- **Account**: used for money transactions.
- **Transaction**: Record of any account transaction.


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


### Running
1. Install docker (https://docs.docker.com/engine/install/)
2. Run docker app locally.
2. Run [docker-compose.yaml](docker%2Fdocker-compose.yaml)
3. Run [CleverBank.java](src%2Fmain%2Fjava%2Fcom%2Fgithub%2Fkaydunov%2FCleverBank.java)

### Lombok
For a successful code compilation with lombok we need:
1. Turn on Annotation Processors:
Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors.

2. Add this to pom.xml:
```xml
<plugin>
   <groupId>org.apache.maven.plugins</groupId>
   <artifactId>maven-compiler-plugin</artifactId>
   <version>3.3</version>
   <configuration>
      <compilerVersion>1.8</compilerVersion>
      <source>1.8</source>
      <target>1.8</target>
      <annotationProcessors>
         <annotationProcessor>lombok.launch.AnnotationProcessorHider$AnnotationProcessor
         </annotationProcessor>
      </annotationProcessors>
   </configuration>
</plugin>
```


[Documentation](docs/index.html)


