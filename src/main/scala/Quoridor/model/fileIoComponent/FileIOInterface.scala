package Quoridor.model.fileIoComponent

import Quoridor.model.boardComponent.BoardInterface

trait FileIOInterface {

  def load: BoardInterface
  def save(): Unit

}
