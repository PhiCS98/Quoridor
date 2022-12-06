package Quoridor.model.boardComponent

import Quoridor.model.boardComponent.boardBaseImpl.{Board, BoardError, Field, Player}

trait BoardInterface {
  def size: Int
  def row(row: Int): Seq[Field]

  def cell(row: Int, col: Int): Field

  def isSet(row: Int, col: Int): Boolean

  def placeWall(row: Int, col: Int, player: Player): Either[BoardError, Board[Field]]

  def movePawn(toRow: Int, toCol: Int, player: Player): Either[BoardError, Board[Field]]
  def replaceCell(row: Int, col: Int, cell: Field): Board[Field]

  def moveIsValid(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int): Boolean

  def returnPositionOfPlayerPawn(player: Player): Option[(Int, Int)]

  def toString: String

  def returnPlayerOfPosition(row: Int, col: Int): Option[Player]

}
