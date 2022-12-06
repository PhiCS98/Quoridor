package controller.ControllerComponent.controllerBaseImpl

import Quoridor.controller.controllerComponent.controllerBaseImpl.Controller
import Quoridor.model.boardComponent.boardBaseImpl.{Board, BoardCreator, PieceField}
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

        controller.undo()
        controller.undo()
        controller.board should be(smallBoard)
      }
      "return a string representation of its board" in {
        val expected: String = smallBoard.toString()
        val controller2 = new Controller(smallBoard)
        controller2.boardToString should be(expected)
      }

      "have a method to return the size of the board associated with the controller" in {
        controller.boardSize should be(17)
      }
      "have a method to return a field of the board associated with the controller" in {
        controller.cell(0, 0) should be(PieceField(None))
      }
      "have a method to check if a field of the board associated with the controller is set" in {
        controller.isSet(0, 0) should be(false)
      }
      "have a method to notify Observers to quit" in {
        controller.quit() should be(true)
      }
    }
  }
}
