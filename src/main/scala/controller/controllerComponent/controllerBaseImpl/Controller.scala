package controller.controllerComponent.controllerBaseImpl

import controller.controllerComponent.ControllerInterface
import controller.controllerComponent.controllerBaseImpl.GameStatus.*
import model.BoardInterface
import model.boardComponent.boardBaseImpl.{Field, Player, Player1, Player2}
import util.Event.{FIELDCHANGED, QUIT}
import util.{Observable, UndoManager}

import scala.collection.mutable

class Controller(var board: BoardInterface) extends ControllerInterface with Observable {

  private val undoManager: UndoManager = new UndoManager
  var gameStatus: GameStatus.Value = GameStatus.MOVED
  private var currentPlayer: Int = 0
  private var playerWallCount = mutable.Map(0 -> 10, 1 -> 10)

  def boardToString: String = board.toString

  def boardSize: Int = board.size

  def cell(row: Int, col: Int): Field = board.cell(row, col)

  def isSet(row: Int, col: Int): Boolean = board.isSet(row, col)

  def quit(): Boolean = { notifyObservers(QUIT); true }

  def createEmptyBoard(size: Int): Boolean = {
    size match {
      case 2 =>
        board = board.createBoardWith2Players()

      case _: Int => false
    }

    notifyObservers(FIELDCHANGED)
    true
  }

  def movePawn(row: Int, col: Int): Unit = {
    val player = returnPlayerById(currentPlayer)
    val oldPosition = board.returnPositionOfPlayerPawn(player).getOrElse((0, 0))
    if (board.moveIsValid(oldPosition._1, oldPosition._2, row, col))
      playerMove(row, col, player)
      cyclePlayers()
      gameStatus = MOVED
      notifyObservers(FIELDCHANGED)
    else gameStatus = GameStatus.MOVED

  }

  def setWall(row: Int, column: Int): Unit = {
    val wallsInPossession = playerWallCount.getOrElseUpdate(currentPlayer, 0)
    if (wallsInPossession != 0)
      val player = returnPlayerById(currentPlayer)
      undoManager.doStep(new PlaceCommand(row, column, player, this))
      cyclePlayerAndSetWall(wallsInPossession)
    else gameStatus = NO_MORE_WALLS

  }

  def undo(): Unit = {
    undoManager.undoStep()
    // gameStatus = UNDO
    cyclePlayers()
    notifyObservers(FIELDCHANGED)
  }

  def redo(): Unit = {
    undoManager.redoStep()
    // gameStatus = REDO
    cyclePlayers()
    notifyObservers(FIELDCHANGED)
  }

  private def playerMove(row: Int, col: Int, player: Player): Unit = {

    undoManager.doStep(new MoveCommand(row, col, player, this))
  }

  private def returnPlayerById(playerID: Int): Player = {
    playerID match {
      case 0 => Player1()
      case 1 => Player2()
    }
  }

  private def cyclePlayerAndSetWall(wallsInPossession: Int): Unit = {
    playerWallCount.update(currentPlayer, wallsInPossession - 1)
    cyclePlayers()
    notifyObservers(FIELDCHANGED)
  }
  private def cyclePlayers(): Unit = currentPlayer = (currentPlayer + 1) % 2
}
