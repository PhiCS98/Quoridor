package Quoridor.controller.controllerComponent.controllerBaseImpl

import Quoridor.controller.controllerComponent.GameStatus.*
import Quoridor.controller.controllerComponent.{ControllerInterface, GameStatus}
import Quoridor.model.boardComponent.BoardInterface
import Quoridor.model.boardComponent.boardBaseImpl.*
import Quoridor.util.Event.{FIELDCHANGED, QUIT}
import Quoridor.util.{Observable, UndoManager}

import scala.collection.mutable

class Controller(var board: BoardInterface) extends ControllerInterface with Observable {

  private val undoManager: UndoManager = new UndoManager
  private var gameStatus: GameStatus.Value = GameStatus.MOVED
  private var currentPlayer: Int = 0
  private var playerWallCount = mutable.Map(0 -> 10, 1 -> 10)

  override def retrieveGameStatus: GameStatus = gameStatus

  override def retrievePlayerAtPosition(row: Int, col: Int): Option[Player] =
    board.returnPlayerOfPosition(row, col)

  def boardToString: String = board.toString

  def boardSize: Int = board.size

  def cell(row: Int, col: Int): Field = board.cell(row, col)

  def isSet(row: Int, col: Int): Boolean = board.isSet(row, col)

  def quit(): Boolean = { notifyObservers(QUIT); true }

  def movePawn(row: Int, col: Int): Unit =
    val player = returnPlayerById(currentPlayer)
    val oldPosition = board.returnPositionOfPlayerPawn(player).getOrElse((0, 0))
    if (board.moveIsValid(oldPosition._1, oldPosition._2, row, col))
      playerMove(row, col, player)
      cyclePlayers()
      gameStatus = MOVED
      notifyObservers(FIELDCHANGED)
    else gameStatus = GameStatus.MOVED

  def setWall(row: Int, column: Int): Unit =
    val wallsInPossession = playerWallCount.getOrElseUpdate(currentPlayer, 0)
    if (wallsInPossession != 0)
      val player = returnPlayerById(currentPlayer)
      undoManager.doStep(new PlaceCommand(row, column, player, this))
      cyclePlayerAndSetWall(wallsInPossession)
    else gameStatus = NO_MORE_WALLS

  def undo(): Unit =
    undoManager.undoStep()
    cyclePlayers()
    notifyObservers(FIELDCHANGED)

  def redo(): Unit = {
    undoManager.redoStep()
    cyclePlayers()
    notifyObservers(FIELDCHANGED)
  }

  private def playerMove(row: Int, col: Int, player: Player): Unit =
    undoManager.doStep(new MoveCommand(row, col, player, this))

  private def returnPlayerById(playerID: Int): Player =
    playerID match {
      case 0 => Player1()
      case 1 => Player2()
    }

  private def cyclePlayerAndSetWall(wallsInPossession: Int): Unit =
    playerWallCount.update(currentPlayer, wallsInPossession - 1)
    cyclePlayers()
    notifyObservers(FIELDCHANGED)

  private def cyclePlayers(): Unit = currentPlayer = (currentPlayer + 1) % 2
}
