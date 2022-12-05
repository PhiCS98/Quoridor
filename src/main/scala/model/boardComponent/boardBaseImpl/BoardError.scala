package model.boardComponent.boardBaseImpl

import model.boardComponent.boardBaseImpl

sealed abstract class BoardError(val message: String)

object BoardError {

  case class AlreadyOccupied(row: Int, col: Int)
      extends BoardError(s"There is already a Piece on $row $col")

  case class SomeWeirdError()
      extends BoardError(s"This is some weird runtime error and should not have happened")

  case class IllegalMove(row: Int, col: Int) extends BoardError(s"$row $col was an illegal move!")

  case class WrongField(row: Int, col: Int)
      extends BoardError(s"$row $col was the wrong field type!")
  
}
