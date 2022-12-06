package model.boardComponent.boardBaseImpl

import model.*
import model.boardComponent.boardBaseImpl
import model.boardComponent.boardBaseImpl.Board

object BoardCreator {

  def createBoardWith4Players(): Board[Field] =
    createBoardWith2Players()
      .replaceCell(8, 0, PieceField(Some(boardBaseImpl.Pawn(Player3()))))
      .replaceCell(8, 16, PieceField(Some(boardBaseImpl.Pawn(Player4()))))

  def createBoardWith2Players(): Board[Field] =
    createEmptyBoard()
      .replaceCell(0, 8, PieceField(Some(boardBaseImpl.Pawn(Player1()))))
      .replaceCell(16, 8, PieceField(Some(boardBaseImpl.Pawn(Player2()))))

  def createEmptyBoard(): Board[Field] = new Board[Field](9)
}
