package Quoridor.model.boardComponent

import Quoridor.model.boardComponent.boardBaseImpl.{Board, BoardError, Field, Player}
import io.circe.generic.auto.*
import io.circe.generic.semiauto.deriveEncoder
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Json}

trait BoardInterface {
  def size: Int

  def row(row: Int): Seq[Field]

  def cell(row: Int, col: Int): Field

  def isSet(row: Int, col: Int): Boolean

  def placeWall(row: Int, col: Int, player: Player): Either[BoardError, Board[Field]]

  def movePawn(toRow: Int, toCol: Int, player: Player): Either[BoardError, Board[Field]]

  def replaceCell(row: Int, col: Int, cell: Field): Board[Field]

  def moveIsValid(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int): Boolean

  def returnPositionOfPlayerPawn(player: Player): Option[(Int, Int)]

  def toString: String

  def returnPlayerOfPosition(row: Int, col: Int): Option[Player]

}
object BoardInterface {
  implicit val encoder: Encoder[BoardInterface] = new Encoder[BoardInterface] {
    def apply(b: BoardInterface): Json = b match {
      case b: Board[Field] => b.asJson
    }
  }
}
