package model

abstract class Piece(player: Player):
  def returnPlayer(): Player = this.player

case class Wall(player: Player) extends Piece(player)

case class Pawn(player: Player) extends Piece(player)
