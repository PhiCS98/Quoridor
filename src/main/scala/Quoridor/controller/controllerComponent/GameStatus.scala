package Quoridor.controller.controllerComponent

object GameStatus extends Enumeration {
  type GameStatus = Value

  val MOVED, NEW, IDLE, UNDO, REDO, ILLEGAL_MOVE, NO_MORE_WALLS = Value

  val map: Map[GameStatus, String] = Map[GameStatus, String](
    IDLE -> "",
    NEW -> "A new game was created",
    MOVED -> "A field was set",
    UNDO -> "Undone one step",
    REDO -> "Redone one step",
    ILLEGAL_MOVE -> "This move was illegal",
    NO_MORE_WALLS -> "Player does not have enough walls ")

  def message(gameStatus: GameStatus): String = {
    map(gameStatus)
  }

}
