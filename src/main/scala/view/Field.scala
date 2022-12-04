package view

import controller.Controller
import javafx.scene.layout.HBox
import model.*
import scalafx.scene.control.Button
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import util.{Event, Observer}


final case class Field(controller: Controller, row: Int, col: Int) extends Button with Observer {

  controller.add(this)
  minWidth = 60
  maxWidth = 60
  minHeight = 60
  maxHeight = 60
  shape = Rectangle(50, 50)

  override def update(e: Event): Unit = setId()

  onAction = _ => controller.movePawn(row, col)

  private def setId(): Unit = {
    if (controller.board.cell(row, col).content.isDefined) {
      controller.board.cell(row, col).content.get.returnPlayer() match {
        case Player1() => id = "blackPawn"
        case Player2() => id = "whitePawn"
        case _ =>
      }
    } else {
      id = "regularField"
    }
  }

  setId()
  visible = true
}
