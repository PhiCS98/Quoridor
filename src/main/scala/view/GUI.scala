package view

import controller.Controller
import model.*
import scalafx.Includes.observableList2ObservableBuffer
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.*
import scalafx.scene.Scene
import scalafx.scene.control.Menu
import scalafx.scene.layout.*
import scalafx.scene.paint.*
import util.*

object GUI extends JFXApp3 {
  val controller = new Controller(BoardCreator.createBoardWith2Players())
  val tui = new TUI(controller)

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Quoridor"
      scene = new Scene {
        stylesheets += getClass.getResource("GUI.css").toExternalForm
        content = new GridPane() {
          id = "pane"
          border = new Border(new BorderStroke(Color.Black, BorderStrokeStyle.Solid, CornerRadii.Empty, new BorderWidths(10)))
          for {
            i <- 0 until controller.boardSize
            j <- 0 until controller.boardSize
          } {
            (i % 2, j % 2) match {
              case (0, 0) => add(Field(controller, j, i), i, j)
              case (0, 1) => add(VerticalWall(controller, j, i), i, j)
              case (1, 0) => add(HorizontalWall(controller, j, i), i, j)
              case (1, 1) => add(BlankSpace(controller, j, i), i, j)
            }
          }
          visible = true
          resizable = false
        }
      }
    }

    val cliThread = new Thread(() => {
      tui.start()
      System.exit(0)
    })
    cliThread.setDaemon(true)
    cliThread.start()
  }

}