package view.tuiComponent

import util.Observer

trait TUIInterface extends Observer {
  def start(): Unit
}
