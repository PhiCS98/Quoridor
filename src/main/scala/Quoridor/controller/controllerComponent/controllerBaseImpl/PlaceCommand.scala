package Quoridor.controller.controllerComponent.controllerBaseImpl

import Quoridor.controller.controllerComponent.ControllerInterface
import Quoridor.model.BoardInterface
import Quoridor.model.boardComponent.boardBaseImpl.{Field, Player}
import Quoridor.util.Command

class PlaceCommand(row: Int, column: Int, player: Player, controller: Controller)
    extends Command {

  private val oldBoard: BoardInterface = controller.board

  override def doStep(): Unit = controller.board = {
    controller.board.placeWall(row, column, player).getOrElse(controller.board)
  }

  override def undoStep(): Unit = {
    controller.board = oldBoard
  }

  override def redoStep(): Unit = controller.board =
    controller.board.placeWall(row, column, player).getOrElse(controller.board)
}
