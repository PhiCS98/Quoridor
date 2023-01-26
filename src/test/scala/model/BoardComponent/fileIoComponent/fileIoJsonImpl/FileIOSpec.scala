package model.BoardComponent.fileIoComponent.fileIoJsonImpl

import Quoridor.model.boardComponent.BoardInterface
import Quoridor.model.boardComponent.boardBaseImpl.{Board, Player1, Player2, Wall, WallField}
import Quoridor.model.fileIoComponent.fileIoJsonImpl.FileIO
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec

import java.io.File

class FileIOSpec extends AnyWordSpec with should.Matchers {
  "A FileIO" should {
    "be able to save and load a board" in {
      val fileIO = new FileIO()

      val board = Board
        .createBoardWith2Players()
        .replaceCell(0, 1, WallField(Some(Wall(Player1()))))
        .replaceCell(0, 3, WallField(Some(Wall(Player2()))))

      val temp = board

      given BoardInterface = board

      fileIO.save
      board.replaceCell(0, 1, WallField(Some(Wall(Player1()))))
      fileIO.load() should be(temp)
    }
  }
}
