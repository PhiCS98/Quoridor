package view

import controller.Controller
import scalafx.scene.control.Button
import scalafx.scene.shape.Rectangle
import util.{Event, Observer}

final case class BlankSpace(controller: Controller, row: Int, col: Int) extends Button with Observer {
  controller.add(this)
  minWidth = 20
  maxWidth = 50
  minHeight = 20
  maxHeight = 30
  shape = Rectangle(50, 30)
  visible = false
  disable = true

  override def update(e: Event): Unit = setId()

  private def setId(): Unit = {
    if (controller.board.cell(row, col).content.isDefined) {
      id = "selectedWall"
      visible = true
    } else {
      id = "unselectedWall"
    }
  }
}
