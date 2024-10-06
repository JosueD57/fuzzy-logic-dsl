package fuzzylogic

object Environment {
  private var variableStack: List[Map[String, Any]] = List(Map())

  // Assigns a value to a variable
  def assign(name: String, value: Any): Unit = {
    val currentScope = variableStack.head + (name -> value)
    variableStack = currentScope :: variableStack.tail
  }

  // Gets a value for a variable,searches the current scope up
  def get[T](name: String): Option[T] = {
    variableStack.collectFirst {
      case scope if scope.contains(name) => scope(name).asInstanceOf[T]
    }
  }

  // Enters a new scope
  def enterScope(): Unit = {
    variableStack = Map[String, Any]() :: variableStack
  }

  // Exits the current scope
  def exitScope(): Unit = {
    variableStack = variableStack.tail
  }
}

