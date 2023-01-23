package model.BoardComponent.boardBaseImpl

import Quoridor.model.boardComponent.boardBaseImpl.Field.{pieceFieldDecoder, pieceFieldEncoder, wallFieldDecoder, wallFieldEncoder}
import Quoridor.model.boardComponent.boardBaseImpl.{Field, Pawn, Piece, PieceField, Player1, Wall, WallField}
import io.circe.*
import io.circe.jawn.decode
import io.circe.syntax.*
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers.should

class FieldSpec extends AnyFunSpec {

  describe("Field") {
    it("should return true when the content is set") {
      val field = new Field {
        override val content: Option[Piece] = Some(Pawn(Player1()))
      }
      assert(field.isSet === true)
    }

    it("should return false when the content is not set") {
      val field = new Field {
        override val content: Option[Piece] = None
      }
      assert(field.isSet === false)
    }
  }

  describe("Field companion object") {

    it("should encode a PieceField correctly") {
      val field = PieceField(Some(Pawn(Player1())))
      val json = field.asJson
      assert(json.toString() === """{
                              |  "fieldType" : "piece",
                              |  "content" : {
                              |    "type" : "pawn",
                              |    "player" : {
                              |      "playerName" : "player1"
                              |    }
                              |  }
                              |}""".stripMargin)
    }

    it("should encode a WallField without content correctly") {
      val field = WallField(None)
      val json = field.asJson
      assert(json.toString() === """{
                                   |  "fieldType" : "wall",
                                   |  "content" : null
                                   |}""".stripMargin)
    }

    it("should encode a WallField with content correctly") {
      val field = WallField(Some(Wall(Player1())))
      val json = field.asJson
      assert(
        json.toString() ===
          """{
            |  "fieldType" : "wall",
            |  "content" : {
            |    "type" : "wall",
            |    "player" : {
            |      "playerName" : "player1"
            |    }
            |  }
            |}""".stripMargin)
    }

    it("should decode a json string correctly") {
      val jsonString = """{
                         |  "fieldType" : "piece",
                         |  "content" : {
                         |    "type" : "pawn",
                         |    "player" : {
                         |      "playerName" : "player1"
                         |    }
                         |  }
                         |}""".stripMargin
      val field = decode[Field](jsonString)
      field should ===(Right(PieceField(Some(Pawn(Player1())))))
    }

    it("should throw a DecodingFailure when decoding wrong json") {
      val jsonString =
        """{
          |  "fieldType" : "tower",
          |  "content" : {
          |    "type" : "pawn",
          |    "player" : {
          |      "playerName" : "player1"
          |    }
          |  }
          |}""".stripMargin
      val field = decode[Field](jsonString)
      field should ===(Left(DecodingFailure("Invalid fieldType", List())))
    }
    it("should handle  decoding wrong json to PieceField") {
      val jsonString =
        """{
          |  "fieldType" : "tower",
          |  "content" : {
          |    "type" : "pawn",
          |    "player" : {
          |      "playerName" : "player1"
          |    }
          |  }
          |}""".stripMargin
      val field = decode[PieceField](jsonString)
      field should ===(Right(PieceField(None)))
    }
    it("should handle decoding wrong json to WallField") {
      val jsonString =
        """{
          |  "fieldType" : "tower",
          |  "content" : {
          |    "type" : "pawn",
          |    "player" : {
          |      "playerName" : "player1"
          |    }
          |  }
          |}""".stripMargin
      val field = decode[WallField](jsonString)
      field should ===(Right(WallField(None)))
    }
  }
}
