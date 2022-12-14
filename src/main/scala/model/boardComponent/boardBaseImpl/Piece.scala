package model.boardComponent.boardBaseImpl

import model.boardComponent.boardBaseImpl

abstract class Piece(player: Player):
  def returnPlayer(): Player = this.player

case class Wall(player: Player) extends Piece(player)

case class Pawn(player: Player) extends Piece(player)
