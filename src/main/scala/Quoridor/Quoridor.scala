package Quoridor

import Quoridor.controller.controllerComponent.controllerBaseImpl.Controller
import Quoridor.view.tuiComponent.tuiBaseImpl.TUI
import Quoridor.model.boardComponent.boardBaseImpl.BoardCreator
import Quoridor.view.guiComponent.guiBaseImpl.GUI
import Quoridor.controller.controllerComponent.ControllerInterface

@main
def main(): Unit = {
  val controller = new Controller(BoardCreator.createBoardWith2Players())
  given ControllerInterface = controller
  val tui = new TUI
  val gui = new GUI

  val cliThread = new Thread(() => {
    tui.start()
    System.exit(0)
  })
  cliThread.setDaemon(true)
  cliThread.start()

  gui.main(Array.empty)

}
