package model.BoardComponent.boardBaseImpl

import Quoridor.model.boardComponent.BoardInterface.encoder
import Quoridor.model.boardComponent.boardBaseImpl
import Quoridor.model.boardComponent.boardBaseImpl.*
import Quoridor.model.boardComponent.boardBaseImpl.Board.encoder
import Quoridor.model.boardComponent.boardBaseImpl.Piece.{decoder, encoder}
import io.circe.*
import io.circe.syntax.*
import org.scalactic.{Equality, TolerantNumerics, TypeCheckedTripleEquals}
import org.scalatest.EitherValues
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec

import java.io.{File, PrintWriter}
import scala.io.Source
import scala.util.{Failure, Success}

class BoardSpec extends AnyWordSpec with should.Matchers with TypeCheckedTripleEquals with EitherValues {

  "A Quoridor board" should {

    "have a method to return its size" in {
      val board = new Board(9)
      val actual = board.size
      val expected = 17
      actual should ===(expected)
    }

    "have a method to return a new empty board" in {
      val board = BoardCreator.createEmptyBoard()

      Board.createEmptyBoard() should be(board)
    }

    "have a method to return a new board with 4 players" in {
      val board = BoardCreator.createBoardWith4Players()

      Board.createBoardWith4Players() should be(board)
    }

    "have a constructor to create a quoridor board" in {
      val actual = new Board(3)
      val expected = Board(
        Vector(
          Vector(PieceField(None), WallField(None), PieceField(None), WallField(None), PieceField(None)),
          Vector(WallField(None), WallField(None), WallField(None), WallField(None), WallField(None)),
          Vector(PieceField(None), WallField(None), PieceField(None), WallField(None), PieceField(None)),
          Vector(WallField(None), WallField(None), WallField(None), WallField(None), WallField(None)),
          Vector(PieceField(None), WallField(None), PieceField(None), WallField(None), PieceField(None))))
      actual should ===(expected)
    }

    "have a method to replace a piece cell of the board" in {
      val board = new Board[Field](3)
      val actual = board.replaceCell(0, 2, PieceField(Some(Pawn(Player1()))))
      val expected = Board[Field](
        Vector(
          Vector(
            PieceField(None),
            WallField(None),
            PieceField(Some(boardBaseImpl.Pawn(Player1()))),
            WallField(None),
            PieceField(None)),
          Vector(WallField(None), WallField(None), WallField(None), WallField(None), WallField(None)),
          Vector(PieceField(None), WallField(None), PieceField(None), WallField(None), PieceField(None)),
          Vector(WallField(None), WallField(None), WallField(None), WallField(None), WallField(None)),
          Vector(PieceField(None), WallField(None), PieceField(None), WallField(None), PieceField(None))))
      actual should ===(expected)
    }
    "have a method to return the possible player of a piece at a given position " in {
      var board = Board.createBoardWith2Players()
      board.returnPlayerOfPosition(0, 8) should ===(Some(Player1()))
      board = Board.createEmptyBoard()
      board.returnPlayerOfPosition(0, 8) should ===(None)
    }

    "should be able to encode and decode to json" in {
      val board = new Board[Field](3)
      val source: String =
        Source.fromFile("./src/test/scala/model/BoardComponent/boardBaseImpl/expected.json").getLines().mkString
      val expected: Json = parser.parse(source).toTry match
        case Success(value) => value
        case Failure(exception) => Json.Null
      board.asJson should ===(expected)
      board.asJson.as[Board[Field]].value should be(board)
    }

    "have a method to place a Wall on the board" when {

      "correct parameters get passed to it" in {
        val board = new Board[Field](3)
        val actual = board.placeWall(0, 1, Player1()).value
        val expected =
          Board[Field](
            Vector(
              Vector(
                PieceField(None),
                WallField(Some(Wall(Player1()))),
                PieceField(None),
                WallField(None),
                PieceField(None)),
              Vector(
                WallField(None),
                WallField(Some(boardBaseImpl.Wall(Player1()))),
                WallField(None),
                WallField(None),
                WallField(None)),
              Vector(
                PieceField(None),
                WallField(Some(boardBaseImpl.Wall(Player1()))),
                PieceField(None),
                WallField(None),
                PieceField(None)),
              Vector(WallField(None), WallField(None), WallField(None), WallField(None), WallField(None)),
              Vector(PieceField(None), WallField(None), PieceField(None), WallField(None), PieceField(None))))
        actual should ===(expected)
      }

      "return the correct board when placing a wall on the lower edge" in {
        val board = new Board[Field](3)
        val actual = board.placeWall(4, 1, Player1()).value
        val expected = Board[Field](
          Vector(
            Vector(PieceField(None), WallField(None), PieceField(None), WallField(None), PieceField(None)),
            Vector(WallField(None), WallField(None), WallField(None), WallField(None), WallField(None)),
            Vector(PieceField(None), WallField(None), PieceField(None), WallField(None), PieceField(None)),
            Vector(WallField(None), WallField(None), WallField(None), WallField(None), WallField(None)),
            Vector(
              PieceField(None),
              WallField(Some(Wall(Player1()))),
              PieceField(None),
              WallField(None),
              PieceField(None))))

        actual should ===(expected)
      }
      "return the correct board when placing a wall on the right edge" in {
        val board = new Board[Field](3)
        val actual = board.placeWall(1, 4, Player1()).value
        val expected = Board[Field](
          Vector(
            Vector(PieceField(None), WallField(None), PieceField(None), WallField(None), PieceField(None)),
            Vector(
              WallField(None),
              WallField(None),
              WallField(None),
              WallField(None),
              WallField(Some(Wall(Player1())))),
            Vector(PieceField(None), WallField(None), PieceField(None), WallField(None), PieceField(None)),
            Vector(WallField(None), WallField(None), WallField(None), WallField(None), WallField(None)),
            Vector(PieceField(None), WallField(None), PieceField(None), WallField(None), PieceField(None))))

        actual should ===(expected)
      }

      "return a BoardError if the wrong parameters get passed to it" in {
        val board = new Board[Field](3)
        val actual = board.placeWall(0, 0, Player1()).left.value
        actual should ===(BoardError.SomeWeirdError())
      }
    }

    "be able to move a players pawn or return a BoardError" when {

      "given the right parameters of a piece field" in {
        val board =
          new Board[Field](3).replaceCell(0, 2, PieceField(Some(boardBaseImpl.Pawn(Player1()))))
        val actual = board.movePawn(2, 2, Player1()).getOrElse(board)
        val expected = Board[Field](
          Vector(
            Vector(PieceField(None), WallField(None), PieceField(None), WallField(None), PieceField(None)),
            Vector(WallField(None), WallField(None), WallField(None), WallField(None), WallField(None)),
            Vector(
              PieceField(None),
              WallField(None),
              PieceField(Some(boardBaseImpl.Pawn(Player1()))),
              WallField(None),
              PieceField(None)),
            Vector(WallField(None), WallField(None), WallField(None), WallField(None), WallField(None)),
            Vector(PieceField(None), WallField(None), PieceField(None), WallField(None), PieceField(None))))
        actual should ===(expected)
      }

      "trying to move to an already occupied field" in {
        val board = new Board[Field](3)
          .replaceCell(0, 2, PieceField(Some(boardBaseImpl.Pawn(Player1()))))
          .replaceCell(2, 2, PieceField(Some(boardBaseImpl.Pawn(Player2()))))
        val actual = board.movePawn(2, 2, Player1()).left.value
        val expected = BoardError.AlreadyOccupied(2, 2)
        actual should ===(expected)
      }
      "moving in a direction blocked by a wall" in {
        val board = new Board[Field](3)
          .replaceCell(0, 2, PieceField(Some(boardBaseImpl.Pawn(Player1()))))
          .replaceCell(1, 2, WallField(Some(boardBaseImpl.Wall(Player2()))))

        val actual = board.movePawn(2, 2, Player1()).left.value
        val expected = BoardError.IllegalMove(2, 2)
        actual should ===(expected)
      }
      "when placing a wall on an occupied field" in {
        val board = new Board[Field](3)
          .replaceCell(0, 1, WallField(Some(Wall(Player1()))))

        board.placeWall(0, 1, Player1()).left.value should be(BoardError.AlreadyOccupied(0, 1))
      }

      "dealing with wrong coordinates " in {
        val board =
          new Board[Field](3).replaceCell(0, 2, PieceField(Some(boardBaseImpl.Pawn(Player1()))))
        val actual = board.movePawn(2, 1, Player1()).left.value
        val expected = BoardError.WrongField(2, 1)
        actual should ===(expected)
      }

      "dealing with extremely wrong coordinates " in {
        val board =
          new Board[Field](3).replaceCell(0, 2, PieceField(Some(boardBaseImpl.Pawn(Player1()))))
        val actual = board.movePawn(20, 100, Player1()).left.value
        val expected = BoardError.IllegalMove(20, 100)
        actual should ===(expected)
      }
      "moving a pawn farther than allowed " in {
        val board =
          new Board[Field](9).replaceCell(0, 2, PieceField(Some(boardBaseImpl.Pawn(Player1()))))
        val actual = board.movePawn(0, 6, Player1()).left.value
        val expected = BoardError.IllegalMove(0, 6)
        actual should ===(expected)
      }

      "trying to move to an occupied Field" in {
        val board = new Board[Field](3)
        val actual = board.movePawn(2, 2, Player1()).left.value
        val expected = BoardError.SomeWeirdError()
        actual should ===(expected)
      }

    }

    "have a method to get the contents of a cell with x and y coordinates" in {
      val board = new Board[Field](3)
      val actual = board.cell(0, 0)
      val expected = PieceField(None)
      actual should ===(expected)
    }

    "have a method to return a row of a given index" in {
      val board = new Board[Field](3)
      val actual = board.row(0)
      val expected = Vector(PieceField(None), WallField(None), PieceField(None), WallField(None), PieceField(None))
      actual should ===(expected)
    }

    "have a toString method that returns a string representation of the board" in {
      val board = new Board[Field](3)
        .replaceCell(0, 2, PieceField(Some(boardBaseImpl.Pawn(Player1()))))
        .replaceCell(4, 2, PieceField(Some(boardBaseImpl.Pawn(Player2()))))
        .replaceCell(1, 2, WallField(Some(boardBaseImpl.Wall(Player2()))))
        .replaceCell(3, 2, WallField(Some(boardBaseImpl.Wall(Player1()))))

      val expected = """□   ♟   □ 
    ◼     
□   □   □ 
    ◼     
□   ♙   □ 
"""
      val actual = board.toString()
      actual should ===(expected)
    }
  }

}
