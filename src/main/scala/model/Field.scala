package model

sealed trait Field {
  def content: Option[Piece]
  def isGiven: Boolean
  def isHighlighted: Boolean
  def isSet: Boolean
  def showCandidates: Boolean
}

case class PieceField(content: Option[Piece], isGiven: Boolean = false, isHighlighted: Boolean = false, showCandidates: Boolean = false)
    extends Field {
  def this(content: Option[Piece]) = this(content: Option[Piece], false, false, false)
  override def isSet: Boolean = content.isDefined
  override def toString: String = {
    content match {
      case Some(Pawn(Player1())) => "\u265F "
      case Some(Pawn(Player2())) => "\u2659 "
      case None                  => "\u25a1 "
      case Some(_)               => "\u25a1 "
    }
  }
}

case class WallField(content: Option[Piece], isGiven: Boolean = false, isHighlighted: Boolean = false, showCandidates: Boolean = false)
    extends Field {
  def this(content: Option[Piece]) = this(content: Option[Piece], false, false, false)
  override def isSet: Boolean = content.isDefined
  override def toString: String = {
    content match {
      case None                  => "  "
      case Some(Wall(Player1())) => "\u25fc "
      case Some(Wall(Player2())) => "\u25fc "
      case Some(_)               => "\u25fc "
    }
  }
}
