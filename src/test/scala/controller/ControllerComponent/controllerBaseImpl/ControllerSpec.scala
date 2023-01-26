package controller.ControllerComponent.controllerBaseImpl

import Quoridor.controller.controllerComponent.GameStatus
import Quoridor.controller.controllerComponent.controllerBaseImpl.Controller
import Quoridor.model.boardComponent.BoardInterface
import Quoridor.model.boardComponent.BoardInterface.encoder
import Quoridor.model.boardComponent.boardBaseImpl.{Board, BoardCreator, Field, PieceField, decoder}
import Quoridor.model.fileIoComponent.FileIOInterface
import Quoridor.model.fileIoComponent.fileIoJsonImpl.FileIO
import io.circe.{Json, parser}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.mutable
import scala.io.Source
import scala.util.{Failure, Success}

class ControllerSpec extends AnyWordSpec with should.Matchers {
  "A Controller" when {
    "empty" should {
      var smallBoard = BoardCreator.createBoardWith2Players()

      given BoardInterface = smallBoard
      val fileIo = new FileIO()
      given FileIOInterface = fileIo
      val controller = new Controller()
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
        val controller2 = new Controller()
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
      "have a method to retreive the player of a piece at a given position" in {
        controller.retrievePlayerAtPosition(0, 0) should be(None)
      }
      "have a method to retrieve the game status as a string" in {
        controller.retrieveGameStatus should be(GameStatus.MOVED)
      }
      "have a method to save its board to a json file" in {
        val board = Board.createBoardWith2Players()
        val controller2 = new Controller(using board, FileIO())
        controller2.save
        val source: String = Source.fromFile("board.json").getLines().mkString
        val json: Json = parser.parse(source).toTry match
          case Success(value) => value
          case Failure(exception) => Json.Null

         


      }
      "have a method to load a board from json" in {
        val temp = controller.board
        controller.save
        controller.load
        controller.board should be(temp)
      }
      "have a method to notify Observers to quit" in {
        controller.quit() should be(true)
      }
    }
  }
}
