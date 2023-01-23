package Quoridor

import Quoridor.controller.controllerComponent.ControllerInterface
import Quoridor.controller.controllerComponent.controllerBaseImpl.Controller
import Quoridor.model.boardComponent.BoardInterface
import Quoridor.model.boardComponent.boardBaseImpl.{Board, BoardCreator}
import Quoridor.model.fileIoComponent.FileIOInterface
import Quoridor.model.fileIoComponent.fileIoXmlImpl.FileIO
//import Quoridor.model.fileIoComponent.fileIoJsonImpl.FileIO
import Quoridor.view.guiComponent.guiBaseImpl.GUI
import Quoridor.view.tuiComponent.tuiBaseImpl.TUI

@main
def main(): Unit =
  val board = Board.createBoardWith2Players()
  given BoardInterface = board
  val fileIO = new FileIO
  given FileIOInterface = fileIO
  val controller = new Controller
  given ControllerInterface = controller

  val tui = new TUI
  val gui = new GUI

  val cliThread = new Thread(() =>
    tui.start()
    System.exit(0)
  )
  cliThread.setDaemon(true)
  cliThread.start()

  gui.main(Array.empty)
