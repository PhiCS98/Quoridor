package Quoridor.model.boardComponent.boardBaseImpl
import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, Json}

abstract class Field {
  val content: Option[Piece]

  def isSet: Boolean = content.isDefined

}

case class PieceField(content: Option[Piece]) extends Field {

  override def isSet: Boolean = content.isDefined

  override def toString: String = {
    content match {
      case Some(Pawn(Player1())) => "\u265F "
      case Some(Pawn(Player2())) => "\u2659 "
      case None => "\u25a1 "
    }
  }
}

case class WallField(content: Option[Piece]) extends Field {

  override def isSet: Boolean = content.isDefined

  override def toString: String = {
    content match {
      case None => "  "
      case Some(Wall(Player1())) => "\u25fc "
      case Some(Wall(Player2())) => "\u25fc "
    }
  }
}

object Field {
  implicit val encoder: Encoder[Field] = new Encoder[Field] {
    final def apply(f: Field): Json = f match {
      case _ @PieceField(content) =>
        Json.obj(
          "fieldType" -> Json.fromString("piece"),
          "content" -> content.map(Encoder[Piece].apply).getOrElse(Json.Null))
      case _ @WallField(content) =>
        Json.obj(
          "fieldType" -> Json.fromString("wall"),
          "content" -> content.map(Encoder[Piece].apply).getOrElse(Json.Null))
    }
  }

  implicit val decoder: Decoder[Field] = (c: HCursor) => {
    for {
      fieldType <- c.downField("fieldType").as[String]
      content <- c.downField("content").as[Option[Piece]]
    } yield {
      fieldType match {
        case "piece" => PieceField(content)
        case "wall" => WallField(content)
      }
    }
  }

}
