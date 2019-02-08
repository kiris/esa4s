package esa4s

import io.circe.generic.extras.{Configuration, ConfiguredJsonCodec, _}

package object model {
  trait Pageable {
    val prevPage: Option[Int]
    val nextPage: Option[Int]
    val totalCount: Int
    val page: Int
    val perPage: Int
    val maxPerPage: Int
  }

  implicit val config: Configuration = Configuration.default.withSnakeCaseMemberNames





  @ConfiguredJsonCodec case class UserInfo (
      name: String,
      screenName: String,
      icon: String
  )

  @ConfiguredJsonCodec case class ErrorResponse(error: String, message: String)
}
