package fuzzylogic

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalactic._
import TolerantNumerics._
import Equality._

class FuzzyLogicDslTest extends AnyFlatSpec with Matchers {

  // Define a tolerance for double comparisons
  implicit val doubleEquality: Equality[Double] = TolerantNumerics.tolerantDoubleEquality(1e-6)

  // Define custom equality for FuzzyElement to compare membership values within the tolerance
  implicit val fuzzyElementEquality: Equality[FuzzyElement] = new Equality[FuzzyElement] {
    def areEqual(a: FuzzyElement, b: Any): Boolean = b match {
      case FuzzyElement(name, membership) =>
        a.name == name && doubleEquality.areEqual(a.membership, membership)
      case _ => false
    }
  }

  "FuzzyOperations" should "perform union correctly" in {
    val setA = FuzzySet("A", List(
      FuzzyElement("x1", 0.2),
      FuzzyElement("x2", 0.7),
      FuzzyElement("x3", 0.5)
    ))

    val setB = FuzzySet("B", List(
      FuzzyElement("x1", 0.6),
      FuzzyElement("x2", 0.3),
      FuzzyElement("x3", 0.5)
    ))

    val result = FuzzyOperations.union(setA, setB)

    val expectedElements = List(
      FuzzyElement("x1", 0.6),
      FuzzyElement("x2", 0.7),
      FuzzyElement("x3", 0.5)
    )

    result.elements should contain theSameElementsAs expectedElements
  }

  it should "perform intersection correctly" in {
    val setA = FuzzySet("A", List(
      FuzzyElement("x1", 0.2),
      FuzzyElement("x2", 0.7),
      FuzzyElement("x3", 0.5)
    ))

    val setB = FuzzySet("B", List(
      FuzzyElement("x1", 0.6),
      FuzzyElement("x2", 0.3),
      FuzzyElement("x3", 0.5)
    ))

    val result = FuzzyOperations.intersection(setA, setB)

    val expectedElements = List(
      FuzzyElement("x1", 0.2),
      FuzzyElement("x2", 0.3),
      FuzzyElement("x3", 0.5)
    )

    result.elements should contain theSameElementsAs expectedElements
  }

  it should "perform complement correctly" in {
    val setA = FuzzySet("A", List(
      FuzzyElement("x1", 0.2),
      FuzzyElement("x2", 0.8)
    ))

    val result = FuzzyOperations.complement(setA)

    val expectedElements = List(
      FuzzyElement("x1", 0.8),
      FuzzyElement("x2", 0.2)
    )

    result.elements should contain theSameElementsAs expectedElements
  }

  it should "perform XOR correctly" in {
    val setA = FuzzySet("A", List(
      FuzzyElement("x1", 0.2),
      FuzzyElement("x2", 0.7),
      FuzzyElement("x3", 0.5)
    ))

    val setB = FuzzySet("B", List(
      FuzzyElement("x1", 0.6),
      FuzzyElement("x2", 0.3),
      FuzzyElement("x3", 0.5)
    ))

    val result = FuzzyOperations.xor(setA, setB)

    val expectedElements = List(
      FuzzyElement("x1", 0.4),
      FuzzyElement("x2", 0.4),
      FuzzyElement("x3", 0.0)
    )

    result.elements should contain theSameElementsAs expectedElements
  }

  "LogicGate" should "evaluate logic gates correctly" in {
    // Define logic gate
    val logicGate1 = LogicGate("logicGate1", (inputs: Map[String, Double]) => {
      val aValue = inputs.getOrElse("A", 0.0)
      val bValue = inputs.getOrElse("B", 0.0)
      Math.min(1.0, aValue + bValue)
    })

    // Assign logic gate
    Environment.enterScope()
    Environment.assign("logicGate1", logicGate1)

    // Assign inputs
    Scope.enterScope()
    Environment.assign("A", 0.5)
    Environment.assign("B", 0.7)

    // Test the logic gate
    val result = TestGate.testGate("logicGate1", Map("A" -> 0.5, "B" -> 0.7))
    result shouldEqual 1.0 +- 1e-6

    // Exit scopes
    Scope.exitScope()
    Environment.exitScope()
  }

  it should "perform alpha-cut correctly" in {
    val setA = FuzzySet("A", List(
      FuzzyElement("x1", 0.2),
      FuzzyElement("x2", 0.5),
      FuzzyElement("x3", 0.8)
    ))

    val alpha = 0.5
    val result = FuzzyOperations.alphaCut(setA, alpha)

    val expectedElements = List(
      FuzzyElement("x2", 0.5),
      FuzzyElement("x3", 0.8)
    )

    result should contain theSameElementsAs expectedElements
  }

  it should "perform addition correctly" in {
    val setA = FuzzySet("A", List(
      FuzzyElement("x1", 0.4),
      FuzzyElement("x2", 0.6)
    ))

    val setB = FuzzySet("B", List(
      FuzzyElement("x1", 0.7),
      FuzzyElement("x2", 0.3)
    ))

    val result = FuzzyOperations.addition(setA, setB)

    val expectedElements = List(
      FuzzyElement("x1", 1.0),
      FuzzyElement("x2", 0.9)
    )

    result.elements should contain theSameElementsAs expectedElements
  }

  it should "perform multiplication correctly" in {
    val setA = FuzzySet("A", List(
      FuzzyElement("x1", 0.4),
      FuzzyElement("x2", 0.6)
    ))

    val setB = FuzzySet("B", List(
      FuzzyElement("x1", 0.7),
      FuzzyElement("x2", 0.3)
    ))

    val result = FuzzyOperations.multiplication(setA, setB)

    val expectedElements = List(
      FuzzyElement("x1", 0.28),
      FuzzyElement("x2", 0.18)
    )

    result.elements should contain theSameElementsAs expectedElements
  }
}

