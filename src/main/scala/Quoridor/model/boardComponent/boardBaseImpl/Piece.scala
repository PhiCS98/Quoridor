package Quoridor.model.boardComponent.boardBaseImpl

import Quoridor.model.boardComponent.boardBaseImpl
import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, Json}

abstract class Piece(player: Player):
  def returnPlayer(): Player = this.player

case class Wall(player: Player) extends Piece(player)

case class Pawn(player: Player) extends Piece(player)

object Piece {
  implicit val encoder: Encoder[Piece] = Encoder.instance {
    case wall: Wall =>
      Json.obj("type" -> "wall".asJson, "player" -> wall.returnPlayer().asJson)
    case pawn: Pawn =>
      Json.obj("type" -> "pawn".asJson, "player" -> pawn.returnPlayer().asJson)
  }
  implicit val decoder: Decoder[Piece] = (c: HCursor) =>
    for {
      pieceType <- c.downField("type").as[String]
      player <- c.downField("player").as[Player]
    } yield {
      pieceType match {
        case "wall" => Wall(player)
        case "pawn" => Pawn(player)
      }
    }
}
