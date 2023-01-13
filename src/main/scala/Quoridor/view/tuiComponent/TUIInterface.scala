package Quoridor.view.tuiComponent

import Quoridor.util.Observer

trait TUIInterface extends Observer {
  def start(): Unit
}
