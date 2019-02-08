package esa4s.model

import io.circe.{Decoder, Encoder, Json}
import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec case class Post (
    number: Int,
    name: String,
    tags: List[String],
    category: String,
    fullName: String,
    wip: Boolean,
    bodyMd: String,
    bodyHtml: String,
    createdAt: String,
    updatedAt: String,
    message: String,
    url: String,
    revisionNumber: Int,
    createdBy: UserInfo,
    updatedBy: UserInfo,
    kind: PostKind,
    commentsCount: Int,
    tasksCount: Int,
    doneTasksCount: Int,
    stargazersCount: Int,
    watchersCount: Int,
    star: Boolean,
    watch: Boolean,
    overlapped: Option[Boolean]
)

@ConfiguredJsonCodec case class Posts (
    posts: List[Post],
    prevPage: Option[Int],
    nextPage: Option[Int],
    totalCount: Int,
    page: Int,
    perPage: Int,
    maxPerPage: Int
) extends Pageable

object Posts {

  sealed abstract class Sort(val value: String)
  object Sort {
    case object Updated extends Sort("updated")
    case object Created extends Sort("created")
    case object Stars extends Sort("stars")
    case object Watches extends Sort("watches")
    case object Comments extends Sort("comments")
    case object BestMatch extends Sort("best_match")
  }

  sealed abstract class Order(val value: String)
  object Order {
    case object Desc extends Sort("desc")
    case object Asc extends Sort("asc")
  }

}

sealed abstract class PostIncluded(value: String)
object PostIncluded {
  case object Comments extends PostIncluded("comments")
  case object CommentsStargazers extends PostIncluded("comments.stargazers")
  case object Stargazers extends PostIncluded("stargazers")
}

sealed abstract class PostKind(val value: String)
object PostKind {
  case object Stock extends PostKind("stock")
  case object Flow extends PostKind("flow")
  def apply(kind: String): PostKind = kind match {
    case "stock" => Stock
    case "flow" => Flow
    case other => throw new IllegalArgumentException(s"unknown kind: $other")
  }

  implicit val kindEncode: Encoder[PostKind] = Encoder.instance(x => Json.fromString(x.value))
  implicit val kindDecode: Decoder[PostKind] = Decoder[String].map(PostKind(_))
}

@ConfiguredJsonCodec case class Revision (
    bodyMd: String,
    number: Int,
    user: String
)
