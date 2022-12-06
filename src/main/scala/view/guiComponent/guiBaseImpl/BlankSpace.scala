package view.guiComponent.guiBaseImpl

import controller.controllerComponent.ControllerInterface
import scalafx.scene.control.Button
import scalafx.scene.shape.Rectangle
import util.{Event, Observer}

final case class BlankSpace(controller: ControllerInterface, row: Int, col: Int)
    extends Button
    with Observer {
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
    if (controller.isSet(row, col)) {
      id = "selectedWall"
      visible = true
    } else {
      id = "unselectedWall"
    }
  }
}
