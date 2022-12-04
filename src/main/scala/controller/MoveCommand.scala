package controller

import model.Player
import util.Command

class MoveCommand(row: Int, column: Int, player: Player, controller: Controller) extends Command {

  private val oldPlayerPosition: (Int, Int) = controller.board.returnPositionOfPlayerPawn(player).getOrElse(row, column)

  override def doStep(): Unit = controller.board = {
    controller.board.movePawn(row, column, player).getOrElse(controller.board)
  }

  override def undoStep(): Unit = {
    controller.board = controller.board.movePawn(oldPlayerPosition._1, oldPlayerPosition._2, player).getOrElse(controller.board)
  }

  override def redoStep(): Unit = controller.board = controller.board.movePawn(row, column, player).getOrElse(controller.board)
}
