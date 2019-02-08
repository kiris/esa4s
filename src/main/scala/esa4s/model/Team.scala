package esa4s.model

import io.circe.generic.extras.ConfiguredJsonCodec
import io.circe.{Decoder, Encoder, Json}

@ConfiguredJsonCodec case class Team(
    name: String,
    privacy: Privacy,
    description: String,
    icon: String
)

@ConfiguredJsonCodec case class Teams(
    teams: List[Team],
    prevPage: Option[Int],
    nextPage: Option[Int],
    totalCount: Int,
    page: Int,
    perPage: Int,
    maxPerPage: Int
) extends Pageable

sealed abstract class Privacy(val value: String)
case object Privacy {
  case object Closed extends Privacy("closed")
  case object Open extends Privacy("open")

  def apply(privacy: String): Privacy = privacy match {
    case "closed" => Closed
    case "open" => Open
    case other => throw new IllegalArgumentException(s"unknown privacy: $other")
  }

  implicit val kindEncode: Encoder[Privacy] = Encoder.instance(x => Json.fromString(x.value))
  implicit val kindDecode: Decoder[Privacy] = Decoder[String].map(Privacy(_))
}

