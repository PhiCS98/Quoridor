package view

import controller.{Controller, GameStatus}
import model.Move
import util.{Event, Observer}

import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}

class TUI(controller: Controller) extends Observer {
  controller.add(this)
  val size = 9
  private var exit = false

  override def update(e: Event): Unit = {
    e match {
      case Event.FIELDCHANGED => printTUI()
      case Event.QUIT => exit = true
    }
  }

  def start(): Unit = {
    loop()
  }

  @tailrec
  private def loop(): Unit = {
    if (!exit) {
      val input = readLine()
      parseMoveFromInput(input)
      loop()
    }
  }

  private def printTUI(): Unit = {
    println(controller.boardToString)
    println(GameStatus.message(controller.gameStatus))
  }

  private def processInputLine(input: String): Option[Move] = {
    input match {
      case "q" => controller.quit(); None
      case "n" => controller.createEmptyBoard(2); None
      case "u" => controller.undo(); None
      case "r" => controller.redo(); None
      case _ =>
        input.split(" ").toList match {

          case row :: col :: Nil =>
            stringToInt(row, col) match {
              case Success(value) => Some(Move("Pawn", value._1, value._2))
              case Failure(exception) => None
            }

          case wall :: row :: col :: Nil =>
            stringToInt(row, col) match {
              case Success(value) => Some(Move("Wall", value._1, value._2))
              case Failure(exception) => None
            }

          case _ => None
        }
    }
  }

  private def stringToInt(row: String, col: String): Try[(Int, Int)] = {
    Try(row.toInt, col.toInt)
  }

  def parseMoveFromInput(input: String): Unit = {

    processInputLine(input) match {
      case Some(Move("Pawn", row, col)) => controller.movePawn(row, col)
      case Some(Move("Wall", row, col)) => controller.setWall(row, col)
      case _ =>
    }
  }
}
