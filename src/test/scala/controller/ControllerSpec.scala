package controller

import controller.Controller
import model.BoardCreator
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec with should.Matchers {
  "A Controller" when {
    "empty" should {
      val smallBoard = BoardCreator.createBoardWith2Players()
      val controller = new Controller(smallBoard)
      "handle undo/redo actions" in {
        controller.redo()
        controller.undo()
        controller.board.isSet(2, 8) should be(false)
        controller.movePawn(2, 8)
        controller.board.isSet(2, 8) should be(true)
        controller.undo()
        controller.board.isSet(2, 8) should be(false)
        controller.board.isSet(0, 8) should be(true)
        controller.redo()
        controller.board.isSet(0, 8) should be(false)
        controller.board.isSet(2, 8) should be(true)
        controller.board.isSet(3, 8) should be(false)
        controller.setWall(3, 8)
        controller.board.isSet(3, 8) should be(true)

        controller.undo()
        controller.board.isSet(3, 8) should be(false)

        controller.redo()
        controller.board.isSet(3, 8) should be(true)

        controller.createEmptyBoard(9)
        controller.undo()
        controller.undo()
        controller.board should be(smallBoard)
      }
      "return a string representation of its board" in {
        val expected: String = smallBoard.toString()
        val controller2 = new Controller(smallBoard)
        controller2.boardToString should be(expected)
      }
      "have a method to create a new quoridor board" in {
        controller.createEmptyBoard(2) should be(true)
      }
    }
  }
}
