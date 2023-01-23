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

import scala.io.Source
import scala.util.{Failure, Success}

class PieceSpec extends AnyWordSpec with should.Matchers with TypeCheckedTripleEquals with EitherValues {

  "A piece" should {
    "have a method to return the player of said piece" in {
      val pawn = boardBaseImpl.Pawn(Player1())
      val wall = boardBaseImpl.Wall(Player1())
      val expected = Player1()
      val actual = pawn.returnPlayer()
      val actual2 = wall.returnPlayer()
      actual shouldBe expected
      actual2 shouldBe expected
    }
    "be able to encode to json" in {
      val board = Board.createBoardWith2Players().replaceCell(1, 8, WallField(Some(Wall(Player2()))))
      val pawn = board.cell(0, 8).content.get
      val wall = board.cell(1, 8).content.get
      val json: Json = parser
        .parse("""{
                    "type" : "pawn",
                    "player" : {
                        "playerName" : "player1"
                    }
                }""")
        .getOrElse(Json.Null)

      pawn.asJson should be(json)

      val json2: Json = parser
        .parse("""{
                    "type" : "wall",
                    "player" : {
                        "playerName" : "player2"
                    }
                }""")
        .getOrElse(Json.Null)
      wall.asJson should be(json2)
    }
    "be able to be encoded from json" in {
      val json: Json = Json.obj("type" -> "pawn".asJson, "player" -> Json.obj("playerName" -> "player1".asJson))
      val expected = Pawn(Player1())
      expected should be(json.as[Piece].value)

      val json2: Json = Json.obj("type" -> "wall".asJson, "player" -> Json.obj("playerName" -> "player1".asJson))
      val expected2 = Wall(Player1())
      expected2 should be(json2.as[Piece].value)

    }
  }
}
