package Quoridor.model.fileIoComponent.fileIoXmlImpl

import Quoridor.model.boardComponent.BoardInterface
import Quoridor.model.boardComponent.boardBaseImpl.{Board, Field, Pawn, Piece, PieceField, Player1, Player2, Wall, WallField}
import Quoridor.model.fileIoComponent.FileIOInterface
import Quoridor.view.guiComponent.guiBaseImpl.Wall

import java.io.{File, PrintWriter}
import scala.xml.{NodeSeq, PrettyPrinter, XML}

class FileIO extends FileIOInterface {

  override def load(): Board[Field] =
    var board: Board[Field] = Board.createBoardWith2Players()
    val file = scala.xml.XML.loadFile("board.xml")
    val size = (file \ "@size").text.toInt

    val fieldNodes = file \\ "field"
    for (field <- fieldNodes) {
      val row: Int = (field \ "@row").text.toInt
      val col: Int = (field \ "@col").text.toInt
      val content: Option[Piece] = (field \ "@content").text.trim() match {
        case "Some(Pawn(Player1()))" => Some(Pawn(Player1()))
        case "Some(Pawn(Player2()))" => Some(Pawn(Player2()))
        case "Some(Wall(Player1()))" => Some(Wall(Player1()))
        case "Some(Wall(Player2()))" => Some(Wall(Player2()))
        case _ => None
      }
      board = (row % 2, col % 2) match {
        case (0, 0) => board.replaceCell(row, col, PieceField(content))
        case (_, _) => board.replaceCell(row, col, WallField(content))
      }
    }
    board

  override def save(using board: BoardInterface): Unit = saveString

  private def saveString(using board: BoardInterface): Unit = {
    val pw = new PrintWriter(new File("board.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(boardToXml(board))
    pw.write(xml)
    pw.close()
  }

  private def boardToXml(board: BoardInterface) = {
    <board size={board.size.toString}>
    {
      for {
        row <- 0 until board.size
        col <- 0 until board.size
      } yield fieldToXml(row, col, board)
    }
    </board>
  }

  private def fieldToXml(row: Int, col: Int, board: BoardInterface) = {
    <field row={row.toString} col={col.toString} content={board.cell(row, col).content.toString}>
    </field>
  }

}
