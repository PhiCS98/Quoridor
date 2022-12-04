package controller

import model.*
import util.Command

class PlaceCommand(row: Int, column: Int, player: Player, controller: Controller) extends Command {

  private val oldBoard: Quoridorboard[Field] = controller.board

  override def doStep(): Unit = controller.board = {
    controller.board.placeWall(row, column, player).getOrElse(controller.board)
  }

  override def undoStep(): Unit = {
    controller.board = oldBoard
  }

  override def redoStep(): Unit = controller.board = controller.board.placeWall(row, column, player).getOrElse(controller.board)
}
