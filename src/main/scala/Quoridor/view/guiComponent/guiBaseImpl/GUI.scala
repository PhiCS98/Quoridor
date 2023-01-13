package Quoridor.view.guiComponent.guiBaseImpl

import Quoridor.controller.controllerComponent.ControllerInterface
import Quoridor.controller.controllerComponent.controllerBaseImpl.Controller
import Quoridor.model.*
import Quoridor.util.*
import Quoridor.view.guiComponent.guiBaseImpl.{HorizontalWall, VerticalWall}
import Quoridor.view.guiComponent.guiBaseImpl.{BlankSpace, Field}
import javafx.event
import scalafx.Includes.observableList2ObservableBuffer
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.*
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.{Menu, MenuBar, MenuItem}
import scalafx.scene.layout.*
import scalafx.scene.paint.*

class GUI()(using controller: ControllerInterface) extends JFXApp3:

  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage:
      title = "Quoridor"
      scene = new Scene:
        stylesheets += this.getClass.getResource("gui.css").toExternalForm
        content = new BorderPane:
          top = new MenuBar:
            val redo = new MenuItem("redo")
            val undo = new MenuItem("undo")
            val save = new MenuItem("save")
            val load = new MenuItem("load")

            redo.onAction = _ => controller.redo()
            undo.onAction = _ => controller.undo()
            save.onAction = _ => controller.save
            load.onAction = _ => controller.load

            val editMenu = new Menu("edit")
            editMenu.items = List(redo, undo, save, load)
            menus = List(editMenu)

          center = new GridPane():
            id = "pane"
            for
              i <- 0 until controller.boardSize
              j <- 0 until controller.boardSize
            do
              (i % 2, j % 2) match
                case (0, 0) => add(Field(j, i), i, j)
                case (0, 1) => add(VerticalWall(j, i), i, j)
                case (1, 0) => add(HorizontalWall(j, i), i, j)
                case (1, 1) => add(BlankSpace(j, i), i, j)

            visible = true
            resizable = false
