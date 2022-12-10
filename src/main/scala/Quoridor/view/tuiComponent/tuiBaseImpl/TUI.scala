package Quoridor.view.tuiComponent.tuiBaseImpl

import Quoridor.controller.controllerComponent.{ControllerInterface, GameStatus}
import Quoridor.util.{Event, Observer}
import Quoridor.view.tuiComponent.TUIInterface

import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}

class TUI()(using controller: ControllerInterface) extends TUIInterface with Observer:
  controller.add(this)
  val size = 9
  private var exit = false

  override def update(e: Event): Unit =
    e match
      case Event.FIELDCHANGED => printTUI()
      case Event.QUIT => exit = true

  private def printTUI(): Unit =
    println(controller.boardToString)
    println(GameStatus.message(controller.retrieveGameStatus))

  def start(): Unit =
    printTitle()
    printTUI()
    loop()

  private def parseMoveFromInput(input: String): Unit =
    processInputLine(input) match
      case Some(Move("Pawn", row, col)) => controller.movePawn(row, col)
      case Some(Move("Wall", row, col)) => controller.setWall(row, col)
      case _ =>

  @tailrec
  private def loop(): Unit =
    if (!exit)
      val input = readLine()
      parseMoveFromInput(input)
      loop()

  private def processInputLine(input: String): Option[Move] =
    input match
      case "q" => controller.quit(); None
      case "u" => controller.undo(); None
      case "r" => controller.redo(); None
      case "s" => controller.save; None
      case null => None
      case _ =>
        input.split(" ").toList match

          case row :: col :: Nil =>
            stringToInt(row, col) match
              case Success(value) => Some(Move("Pawn", value._1, value._2))
              case Failure(_) => None

          case _ :: row :: col :: Nil =>
            stringToInt(row, col) match
              case Success(value) => Some(Move("Wall", value._1, value._2))
              case Failure(_) => None

          case _ => None

  private def printTitle(): Unit = println("""
      |██████╗ ██╗   ██╗ ██████╗ ██████╗ ██╗██████╗  ██████╗ ██████╗
      |██╔═══██╗██║   ██║██╔═══██╗██╔══██╗██║██╔══██╗██╔═══██╗██╔══██╗
      |██║   ██║██║   ██║██║   ██║██████╔╝██║██║  ██║██║   ██║██████╔╝
      |██║▄▄ ██║██║   ██║██║   ██║██╔══██╗██║██║  ██║██║   ██║██╔══██╗
      |╚██████╔╝╚██████╔╝╚██████╔╝██║  ██║██║██████╔╝╚██████╔╝██║  ██║
      | ╚══▀▀═╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═╝
      |
      |""".stripMargin)

  private def stringToInt(row: String, col: String): Try[(Int, Int)] =
    Try(row.toInt, col.toInt)

case class Move(piece: String, row: Int, col: Int)
