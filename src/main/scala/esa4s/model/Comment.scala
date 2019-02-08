package esa4s.model

import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec case class Comment(
    id: Int,
    bodyMd: String,
    bodyHtml: String,
    createdAt: String,
    updatedAt: String,
    url: String,
    createdBy: UserInfo
)

@ConfiguredJsonCodec case class Comments(
    comments: List[Comment],
    prevPage: Option[Int],
    nextPage: Option[Int],
    totalCount: Int,
    page: Int,
    perPage: Int,
    maxPerPage: Int
) extends Pageable

sealed abstract class CommentIncluded(value: String)
case object CommentIncluded {
  case object Stargazers extends CommentIncluded("stargazers")
}