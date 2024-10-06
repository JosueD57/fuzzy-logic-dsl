package fuzzylogic

object FuzzyOperations {

  def union(setA: FuzzySet, setB: FuzzySet): FuzzySet = {
    val combinedElements = (setA.elements ++ setB.elements)
      .groupBy(_.name)
      .map { case (name, elements) =>
        val maxMembership = elements.map(_.membership).max
        FuzzyElement(name, maxMembership)
      }.toList
    FuzzySet(s"${setA.name}_union_${setB.name}", combinedElements)
  }

  def intersection(setA: FuzzySet, setB: FuzzySet): FuzzySet = {
    val commonNames = setA.elements.map(_.name).toSet.intersect(setB.elements.map(_.name).toSet)
    val intersectedElements = commonNames.map { name =>
      val membershipA = setA.getMembership(name).getOrElse(0.0)
      val membershipB = setB.getMembership(name).getOrElse(0.0)
      FuzzyElement(name, Math.min(membershipA, membershipB))
    }.toList
    FuzzySet(s"${setA.name}_intersection_${setB.name}", intersectedElements)
  }

  def complement(set: FuzzySet): FuzzySet = {
    val complementedElements = set.elements.map { element =>
      FuzzyElement(element.name, 1.0 - element.membership)
    }
    FuzzySet(s"${set.name}_complement", complementedElements)
  }

  def addition(setA: FuzzySet, setB: FuzzySet): FuzzySet = {
    val combinedElements = (setA.elements ++ setB.elements)
      .groupBy(_.name)
      .map { case (name, elements) =>
        val sumMembership = elements.map(_.membership).sum
        FuzzyElement(name, Math.min(1.0, sumMembership))
      }.toList
    FuzzySet(s"${setA.name}_addition_${setB.name}", combinedElements)
  }

  def multiplication(setA: FuzzySet, setB: FuzzySet): FuzzySet = {
    val commonNames = setA.elements.map(_.name).toSet.intersect(setB.elements.map(_.name).toSet)
    val multipliedElements = commonNames.map { name =>
      val membershipA = setA.getMembership(name).getOrElse(0.0)
      val membershipB = setB.getMembership(name).getOrElse(0.0)
      FuzzyElement(name, membershipA * membershipB)
    }.toList
    FuzzySet(s"${setA.name}_multiplication_${setB.name}", multipliedElements)
  }

  def xor(setA: FuzzySet, setB: FuzzySet): FuzzySet = {
    val allNames = setA.elements.map(_.name).toSet.union(setB.elements.map(_.name).toSet)
    val xorElements = allNames.map { name =>
      val membershipA = setA.getMembership(name).getOrElse(0.0)
      val membershipB = setB.getMembership(name).getOrElse(0.0)
      val result = Math.abs(membershipA - membershipB)
      FuzzyElement(name, result)
    }.toList
    FuzzySet(s"${setA.name}_xor_${setB.name}", xorElements)
  }

  def alphaCut(set: FuzzySet, alpha: Double): List[FuzzyElement] = {
    set.elements.filter(_.membership >= alpha)
  }
}
