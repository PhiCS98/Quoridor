package Quoridor.model.boardComponent.boardBaseImpl
import io.circe.syntax.*
import io.circe.{Decoder, DecodingFailure, Encoder, HCursor, Json}

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
  implicit val fieldEncoder: Encoder[Field] =
    Encoder.instance[Field] {
      case pieceField: PieceField => pieceFieldEncoder(pieceField)
      case wallField: WallField => wallFieldEncoder(wallField)
    }

  implicit val fieldDecoder: Decoder[Field] =
    Decoder.instance[Field](cursor =>
      cursor.downField("fieldType").as[String].flatMap {
        case "piece" => pieceFieldDecoder(cursor)
        case "wall" => wallFieldDecoder(cursor)
        case _ => Left(DecodingFailure("Invalid fieldType", cursor.history))
      })

  implicit val pieceFieldEncoder: Encoder[PieceField] =
    (a: PieceField) =>
      Json.obj(
        "fieldType" -> Json.fromString("piece"),
        "content" -> a.content.map(Encoder[Piece].apply).getOrElse(Json.Null))

  implicit val pieceFieldDecoder: Decoder[PieceField] = (c: HCursor) =>
    for {
      fieldType <- c.downField("fieldType").as[String]
      content <- c.downField("content").as[Option[Piece]]
    } yield {
      if (fieldType == "piece") PieceField(content)
      else PieceField(None)
    }

  implicit val wallFieldEncoder: Encoder[WallField] =
    (a: WallField) =>
      Json.obj(
        "fieldType" -> Json.fromString("wall"),
        "content" -> a.content.map(Encoder[Piece].apply).getOrElse(Json.Null))

  implicit val wallFieldDecoder: Decoder[WallField] = (c: HCursor) =>
    for {
      fieldType <- c.downField("fieldType").as[String]
      content <- c.downField("content").as[Option[Piece]]
    } yield {
      if (fieldType == "wall") WallField(content)
      else WallField(None)
    }
}
