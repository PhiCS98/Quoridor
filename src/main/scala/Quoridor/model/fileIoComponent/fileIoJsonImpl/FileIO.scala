package Quoridor.model.fileIoComponent.fileIoJsonImpl

import Quoridor.model.boardComponent.BoardInterface
import Quoridor.model.boardComponent.BoardInterface.encoder
import Quoridor.model.boardComponent.boardBaseImpl.{Board, Field, decoder}
import Quoridor.model.fileIoComponent.FileIOInterface
import io.circe.*
import io.circe.jawn.decodeFile
import io.circe.syntax.*
import java.io.{File, FileNotFoundException, FileReader, PrintWriter}
import scala.io.*
import scala.util.Success
import scala.util.Failure

class FileIO extends FileIOInterface {

  override def load(): Board[Field] =
    var b = Board.createBoardWith2Players()
    val source: String = Source.fromFile("board.json").getLines().mkString
    val json: Json = parser.parse(source).toTry match
      case Success(value) => value
      case Failure(exception) => Json.Null
    b = json.as[Board[Field]].getOrElse(b)
    b

  override def save(using board: BoardInterface): Unit =
    val pw = new PrintWriter(new File("board.json"))
    pw.write(board.asJson.spaces4)
    pw.close()
}
