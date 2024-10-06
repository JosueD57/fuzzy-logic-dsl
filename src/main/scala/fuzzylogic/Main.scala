package fuzzylogic

object Main {
  def main(args: Array[String]): Unit = {

    // Initialize environment
    Environment.enterScope()

    // Define fuzzy elements
    val x1 = FuzzyElement("x1", 0.2)
    val x2 = FuzzyElement("x2", 0.8)
    val x3 = FuzzyElement("x3", 0.5)
    val x4 = FuzzyElement("x4", 1.0)

    // Defines fuzzy sets
    val A = FuzzySet("A", List(x1, x2, x3, x4))
    val B = FuzzySet("B", List(
      FuzzyElement("x1", 0.5),
      FuzzyElement("x2", 0.6),
      FuzzyElement("x3", 0.7),
      FuzzyElement("x4", 0.9)
    ))

    // Assigns fuzzy sets
    Environment.assign("A", A)
    Environment.assign("B", B)

    // Performs operations
    val unionSet = FuzzyOperations.union(A, B)
    val intersectionSet = FuzzyOperations.intersection(A, B)
    val complementSet = FuzzyOperations.complement(A)

    // Assigns the results to variables
    Environment.assign("unionSet", unionSet)
    Environment.assign("intersectionSet", intersectionSet)
    Environment.assign("complementSet", complementSet)

    // Defines logic gates
    val logicGate1 = LogicGate("logicGate1", (inputs: Map[String, Double]) => {
      // Logic for logicGate1 using inputs
      val aValue = inputs.getOrElse("A", 0.0)
      val bValue = inputs.getOrElse("B", 0.0)
      Math.min(1.0, aValue + bValue) // Fuzzy addition
    })

    // Assign logic gate to variable
    Environment.assign("logicGate1", logicGate1)

    // Set inputs within scope
    Scope.enterScope()
    Environment.assign("A", 0.5)
    Environment.assign("B", 0.7)

    // Tests the logic gate
    val testResult = TestGate.testGate("logicGate1", Map("A" -> 0.5, "B" -> 0.7))
    println(s"Test Result of logicGate1: $testResult")

    // Exit scopes
    Scope.exitScope()
    Environment.exitScope()
  }
}
