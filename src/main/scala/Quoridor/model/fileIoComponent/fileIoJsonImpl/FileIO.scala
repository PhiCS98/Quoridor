package Quoridor.model.fileIoComponent.fileIoJsonImpl

import Quoridor.model.boardComponent.BoardInterface
import Quoridor.model.boardComponent.boardBaseImpl.{Field, Piece, PieceField}
import Quoridor.model.fileIoComponent.FileIOInterface
import play.api.libs.json.*

import java.io.{File, PrintWriter}
import scala.io.Source

class FileIO(using var board: BoardInterface) extends FileIOInterface {

  implicit val fieldWrites: Writes[Field] = (o: Field) => Json.obj("content" -> o.content, "set" -> o.isSet)
  implicit val pieceWrites: Writes[Piece] = (o: Piece) =>
    Json.obj("player" -> o.returnPlayer().toString, "type" -> o.getClass.toString)

  /* override def load: BoardInterface =
    val source: String = Source.fromFile("board.json").getLines.mkString
   val json: JsValue = Json.parse(source)
   val size = (json \ "board" \ "size").get.toString.toInt
    board = for (index <- 0 until size * size) {
      println(index.toString)
    }*/

  /*
  override def save(): Unit =
    val pw = PrintWriter(new File("grid.json"))
    pw.write(Json.prettyPrint(boardToJson(board)))
    pw.close()

  private def boardToJson(board: BoardInterfac,): JsObject = Json.obj(
    "board" -> Json.obj(
      "size" -> JsNumber(board.size),
      "cells" -> Json.toJson(for {
        row <- 0 until board.size
        col <- 0 until board.size
      } yield {
        Json.obj("row" -> row, "col" -> col, "cell" -> Json.toJson(board.cell(row, col)))
      })))


   */

  override def load: BoardInterface = ???

  override def save(): Unit = ???
}
