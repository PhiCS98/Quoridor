package model.BoardComponent.boardBaseImpl

import model.boardComponent.boardBaseImpl
import model.boardComponent.boardBaseImpl.*
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec

class BoardCreatorSpec extends AnyWordSpec with should.Matchers with TypeCheckedTripleEquals {
  "A BoardCreator" should {
    "have a method to create a new Quoridorboard of size 9 with 2 players on it" in {
      val actual = BoardCreator.createBoardWith2Players()
      val expected = new Board[Field](9)
        .replaceCell(0, 8, PieceField(Some(Pawn(Player1()))))
        .replaceCell(16, 8, PieceField(Some(boardBaseImpl.Pawn(Player2()))))

      actual should ===(expected)
    }

    "have a method to create a new Quoridorboard of size 9 with 4 players on it" in {
      val actual = BoardCreator.createBoardWith4Players()
      val expected = new Board[Field](9)
        .replaceCell(0, 8, PieceField(Some(boardBaseImpl.Pawn(Player1()))))
        .replaceCell(16, 8, PieceField(Some(boardBaseImpl.Pawn(Player2()))))
        .replaceCell(8, 0, PieceField(Some(boardBaseImpl.Pawn(Player3()))))
        .replaceCell(8, 16, PieceField(Some(boardBaseImpl.Pawn(Player4()))))

      actual should ===(expected)
    }
  }
}
