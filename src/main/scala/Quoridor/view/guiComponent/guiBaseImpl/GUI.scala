package Quoridor.view.guiComponent.guiBaseImpl

import Quoridor.controller.controllerComponent.controllerBaseImpl.Controller
import Quoridor.model.*
import Quoridor.model.boardComponent.boardBaseImpl.BoardCreator
import scalafx.Includes.observableList2ObservableBuffer
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.*
import scalafx.scene.Scene
import scalafx.scene.control.Menu
import scalafx.scene.layout.*
import scalafx.scene.paint.*
import Quoridor.util.*
import Quoridor.view.tuiComponent.tuiBaseImpl.TUI
import Quoridor.view.guiComponent.guiBaseImpl.BlankSpace
import Quoridor.view.guiComponent.guiBaseImpl.Field
import Quoridor.view.VerticalWall
import Quoridor.view.HorizontalWall
import scalafx.scene.control.MenuBar
import scalafx.scene.control.MenuItem
import scalafx.event.ActionEvent
import javafx.event.EventHandler
import scalafx.beans.property.ObjectProperty
import javafx.event
import Quoridor.controller.controllerComponent.ControllerInterface

class GUI(val controller: ControllerInterface) extends JFXApp3 {

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Quoridor"
      scene = new Scene {
        stylesheets += this.getClass.getResource("gui.css").toExternalForm
        content = {
          new BorderPane {
            top = new MenuBar {
              val redo = new MenuItem("redo")
              val undo = new MenuItem("undo")

              redo.onAction = _ => controller.redo()
              undo.onAction = _ => controller.undo()

              val editMenu = new Menu("edit")
              editMenu.items = List(redo, undo)
              menus = List(editMenu)
            }
            center = new GridPane() {
              id = "pane"
              // border = new Border(
              // new BorderStroke(
              //   Color.Black,
              //   BorderStrokeStyle.Solid,
              //   CornerRadii.Empty,
              //   new BorderWidths(5)))
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
      }
    }
  }
}
