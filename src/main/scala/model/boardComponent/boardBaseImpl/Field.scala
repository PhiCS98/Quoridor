package model.boardComponent.boardBaseImpl

sealed trait Field {
  def content: Option[Piece]

  def isSet: Boolean

}

case class PieceField(content: Option[Piece]) extends Field {

  override def isSet: Boolean = content.isDefined

  override def toString: String = {
    content match {
      case Some(Pawn(Player1())) => "\u265F "
      case Some(Pawn(Player2())) => "\u2659 "
      case None => "\u25a1 "
    }
  }
}

case class WallField(content: Option[Piece]) extends Field {

  override def isSet: Boolean = content.isDefined

  override def toString: String = {
    content match {
      case None => "  "
      case Some(Wall(Player1())) => "\u25fc "
      case Some(Wall(Player2())) => "\u25fc "
    }
  }
}
