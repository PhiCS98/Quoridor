package Quoridor.view.guiComponent.guiBaseImpl

import Quoridor.controller.controllerComponent.ControllerInterface
import Quoridor.util.{Event, Observer}
import scalafx.scene.control.Button
import scalafx.scene.shape.Rectangle

final case class BlankSpace(row: Int, col: Int)(using controller: ControllerInterface) extends Button with Observer:
  controller.add(this)
  minWidth = 20
  maxWidth = 50
  minHeight = 20
  maxHeight = 30
  shape = Rectangle(50, 30)
  visible = false
  disable = true

  override def update(e: Event): Unit = setId()

  private def setId(): Unit =
    if (controller.isSet(row, col))
      id = "selectedWall"
      visible = true
    else id = "unselectedWall"
