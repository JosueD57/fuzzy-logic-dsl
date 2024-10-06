package fuzzylogic

case class FuzzySet(name: String, elements: List[FuzzyElement]) {
  // Gets membership value of an element by name
  def getMembership(elementName: String): Option[Double] = {
    elements.find(_.name == elementName).map(_.membership)
  }
}
