FuzzyLogicDSL
Description:
FuzzyLogicDSL is a domain-specific language (DSL) implemented in Scala for 
designing and evaluating fuzzy logic gates using variables and scopes. It allows to:
- Define elements and sets which are both fuzzy
- Create operations (union, intersection, complement, addition, multiplication, XOR, α-cut)
- Help make variables and manage scopes
- Define and evaluate logic gates
- test logic

SetUp
- Java Development Kit (JDK) 8 or higher
- Scala (compatible verison)
- SBT
- IntelliJ IDEA with Scala plugin 

Clone Repository 
- git clone repo
- cd FuzzyLogicDSL

Build Project
- sbt clean compile
- sbt run
- sbt test

Project Structure
- src/main/scala/fuzzylogic/: Contains the source code for the DSL.
- src/test/scala/fuzzylogic/: Contains unit tests.
- build.sbt: SBT build configuration file.

Josue Diaz

