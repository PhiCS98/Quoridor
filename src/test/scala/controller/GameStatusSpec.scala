package controller

import controller.GameStatus.{GameStatus, IDLE}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec

class GameStatusSpec extends AnyWordSpec with should.Matchers {
  "A GameStatus object" should {
    "have a method to return the string of a given status" in {
      val gameStatus: GameStatus = IDLE
      GameStatus.message(gameStatus) should be("")
    }
  }
}
