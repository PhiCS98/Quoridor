package model.BoardComponent.boardBaseImpl

import Quoridor.model.boardComponent.boardBaseImpl
import Quoridor.model.boardComponent.boardBaseImpl.*
import Quoridor.model.boardComponent.boardBaseImpl.Piece.{decoder, encoder}
import Quoridor.model.boardComponent.boardBaseImpl.Player.encoder
import io.circe.*
import io.circe.syntax.*
import org.scalactic.{Equality, TolerantNumerics, TypeCheckedTripleEquals}
import org.scalatest.EitherValues
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec

import scala.io.Source
import scala.util.{Failure, Success}

class PlayerSpec extends AnyWordSpec with should.Matchers with TypeCheckedTripleEquals with EitherValues {

  "A player" should {
    "have a method to encode itself to json" in {
      var playerPiece = Pawn(Player1())
      var expected = Json.obj("playerName" -> "player1".asJson)
      expected should be(playerPiece.returnPlayer().asJson)

      playerPiece = Pawn(Player2())
      expected = Json.obj("playerName" -> "player2".asJson)
      expected should be(playerPiece.returnPlayer().asJson)

      playerPiece = Pawn(Player3())
      expected = Json.obj("playerName" -> "player3".asJson)
      expected should be(playerPiece.returnPlayer().asJson)

      playerPiece = Pawn(Player4())
      expected = Json.obj("playerName" -> "player4".asJson)
      expected should be(playerPiece.returnPlayer().asJson)
    }
    "have a method to decode json to a player object" in {
      val expected = Player1()
      var json = Json.obj("playerName" -> "player1".asJson)
      expected should be(json.as[Player].value)

      val expected2 = Player2()
      json = Json.obj("playerName" -> "player2".asJson)
      expected2 should be(json.as[Player].value)

      val expected3 = Player3()
      json = Json.obj("playerName" -> "player3".asJson)
      expected3 should be(json.as[Player].value)

      val expected4 = Player4()
      json = Json.obj("playerName" -> "player4".asJson)
      expected4 should be(json.as[Player].value)
    }
  }
}
