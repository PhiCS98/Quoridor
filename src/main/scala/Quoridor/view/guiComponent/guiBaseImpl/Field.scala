package Quoridor.view.guiComponent.guiBaseImpl

import Quoridor.controller.controllerComponent.ControllerInterface
import javafx.scene.layout.HBox
import Quoridor.model.boardComponent.boardBaseImpl.{Player1, Player2}
import scalafx.scene.control.Button
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import Quoridor.util.{Event, Observer}

final case class Field(controller: ControllerInterface, row: Int, col: Int)
    extends Button
    with Observer {

  controller.add(this)
  minWidth = 60
  maxWidth = 60
  minHeight = 60
  maxHeight = 60
  shape = Rectangle(50, 50)

  override def update(e: Event): Unit = setId()

  onAction = _ => controller.movePawn(row, col)

  private def setId(): Unit = {
    if (controller.isSet(row, col)) {
      controller.retrievePlayerAtPosition(row, col) match {
        case Some(value) =>
          value match {
            case Player1() => id = "blackPawn"
            case Player2() => id = "whitePawn"
          }
        case None =>
      }
    } else {
      id = "regularField"
    }
  }

  setId()
  visible = true
}
