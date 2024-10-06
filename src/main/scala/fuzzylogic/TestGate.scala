package fuzzylogic

object TestGate {
  def testGate(gateName: String, inputs: Map[String, Double]): Double = {
    Environment.get[LogicGate](gateName) match {
      case Some(gate) =>
        gate.evaluate(inputs)
      case None =>
        throw new Exception(s"Logic gate '$gateName' not found.")
    }
  }
}

