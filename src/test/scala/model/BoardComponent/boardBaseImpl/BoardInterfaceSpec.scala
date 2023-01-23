package model.BoardComponent.boardBaseImpl

import Quoridor.model.boardComponent.BoardInterface
import Quoridor.model.boardComponent.boardBaseImpl.*
import io.circe.Json
import io.circe.parser.*
import io.circe.syntax.*
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class BoardInterfaceSpec extends AnyFunSpec with Matchers {
  describe("Encoding a Board[Field]") {
    it("should encode to json using the encoder provided in the companion object") {
      val board = new Board[Field](9)
      val json = BoardInterface.encoder.apply(board)
      json.hcursor.downField("board").focus shouldBe None
    }
  }
}
