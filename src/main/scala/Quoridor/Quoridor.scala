package Quoridor

import Quoridor.controller.controllerComponent.controllerBaseImpl.Controller
import Quoridor.view.tuiComponent.tuiBaseImpl.TUI
import Quoridor.model.boardComponent.boardBaseImpl.BoardCreator
import Quoridor.view.guiComponent.guiBaseImpl.GUI

@main
def main(): Unit = {
  println("Hello world!")
  val controller = new Controller(BoardCreator.createBoardWith2Players())
  val tui = new TUI(controller)
  val gui = new GUI(controller)

  gui.main(Array.empty)

  val cliThread = new Thread(() => {
    tui.start()
    System.exit(0)
  })
  cliThread.setDaemon(true)
  cliThread.start()

}
