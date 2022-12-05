package controller.controllerComponent

import model.boardComponent.boardBaseImpl.Field
import util.Observable

trait ControllerInterface extends Observable {
  def createEmptyBoard(size: Int): Boolean

  def movePawn(row: Int, col: Int): Unit

  def boardToString: String

  def setWall(row: Int, col: Int): Unit

  def undo(): Unit

  def redo(): Unit

  def boardSize: Int

  def cell(row: Int, col: Int): Field

  def isSet(row: Int, col: Int): Boolean

  def quit(): Boolean

}
