package fuzzylogic

object Scope {
  // Enters a new scope by interacting with Environment
  def enterScope(): Unit = {
    Environment.enterScope()
  }

  // Exits the current scope
  def exitScope(): Unit = {
    Environment.exitScope()
  }
}
