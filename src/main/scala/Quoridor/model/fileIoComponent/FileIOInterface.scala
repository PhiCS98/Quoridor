package Quoridor.model.fileIoComponent

import Quoridor.model.boardComponent.BoardInterface
import Quoridor.model.boardComponent.boardBaseImpl.{Board, Field}

trait FileIOInterface {

  def load(): Board[Field]
  def save(board: BoardInterface): Unit

}
