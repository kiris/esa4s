package esa4s.model

import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec case class Member(
    name: String,
    screenName: String,
    icon: String,
    email: String,
    postsCount: Int
)

@ConfiguredJsonCodec case class Members (
    members: List[Member],
    prevPage: Option[Int],
    nextPage: Option[Int],
    totalCount: Int,
    page: Int,
    perPage: Int,
    maxPerPage: Int
) extends Pageable

