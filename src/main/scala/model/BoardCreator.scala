package model

object BoardCreator {

  def createBoardWith4Players(): Quoridorboard[Field] =
    createBoardWith2Players()
      .replaceCell(8, 0, PieceField(Some(Pawn(Player3()))))
      .replaceCell(8, 16, PieceField(Some(Pawn(Player4()))))

  def createBoardWith2Players(): Quoridorboard[Field] =
    createEmptyBoard()
      .replaceCell(0, 8, PieceField(Some(Pawn(Player1()))))
      .replaceCell(16, 8, PieceField(Some(Pawn(Player2()))))

  def createEmptyBoard(): Quoridorboard[Field] = new Quoridorboard[Field](9)
}
