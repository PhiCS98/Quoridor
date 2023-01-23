package Quoridor.model.boardComponent.boardBaseImpl

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, Json}

sealed trait Player

case class Player1() extends Player

case class Player2() extends Player

case class Player3() extends Player

case class Player4() extends Player

object Player {
  implicit val encoder: Encoder[Player] = Encoder.instance {
    case player1: Player1 => Json.obj("playerName" -> "player1".asJson)
    case player2: Player2 => Json.obj("playerName" -> "player2".asJson)
    case player3: Player3 => Json.obj("playerName" -> "player3".asJson)
    case player4: Player4 => Json.obj("playerName" -> "player4".asJson)
  }
  implicit val decoder: Decoder[Player] = (c: HCursor) =>
    for {
      playerType <- c.downField("playerName").as[String]
    } yield {
      playerType match {
        case "player1" => Player1()
        case "player2" => Player2()
        case "player3" => Player3()
        case "player4" => Player4()
      }
    }
}
