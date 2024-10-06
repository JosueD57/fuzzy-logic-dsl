package fuzzylogic

case class LogicGate(name: String, operation: Map[String, Double] => Double) {
  // Gets the logic gate with the inputs
  def evaluate(inputs: Map[String, Double]): Double = {
    operation(inputs)
  }
}
