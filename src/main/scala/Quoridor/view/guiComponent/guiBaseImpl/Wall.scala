package Quoridor.view.guiComponent.guiBaseImpl

import Quoridor.controller.controllerComponent.ControllerInterface
import Quoridor.util.{Event, Observer}
import scalafx.geometry.Insets
import scalafx.scene.control.Button
import scalafx.scene.layout.{Background, BackgroundFill}
import scalafx.scene.paint.Color.*
import scalafx.scene.shape.Rectangle

sealed trait Wall extends Button {
  visible = true
}

case class VerticalWall(row: Int, col: Int)(using controller: ControllerInterface) extends Wall with Observer:
  controller.add(this)
  id = "unselectedWall"
  minHeight = 30
  maxHeight = 30
  minWidth = 60
  maxWidth = 60
  shape = Rectangle(20, 50)

  onAction = _ => controller.setWall(row, col)

  override def update(e: Event): Unit = setId()

  private def setId(): Unit =
    if (controller.isSet(row, col))
      id = "selectedWall"
    else
      id = "unselectedWall"

  visible = true

case class HorizontalWall(row: Int, col: Int)(using controller: ControllerInterface) extends Wall with Observer:
  controller.add(this)
  id = "unselectedWall"
  minHeight = 60
  maxHeight = 60
  minWidth = 30
  maxWidth = 30
  shape = Rectangle(50, 20)

  onAction = _ => controller.setWall(row, col)

  override def update(e: Event): Unit = setId()

  private def setId(): Unit =
    if (controller.isSet(row, col))
      id = "selectedWall"
    else
      id = "unselectedWall"

  visible = true
