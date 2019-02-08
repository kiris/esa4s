package esa4s.api

import esa4s.EsaResponse
import esa4s.algebra._
import esa4s.model._
import io.circe.generic.extras.ConfiguredJsonCodec
import Posts._
import EsaPosts._

class EsaPosts[F[_]](httpClient: HttpClient[F], team: String) extends PostsAlgebra[F] {
  override def list(
      query: Option[String] = None,
      included: Set[PostIncluded] = Set.empty,
      sort: Option[Sort] = None,
      order: Option[Order] = None,
      page: Option[Int] = None,
      perPage: Option[Int] = None
  ): F[EsaResponse[Posts]] = {
    httpClient.get(
      path = s"/v1/teams/$team/posts",
      params = Map(
        "q" -> query,
        "sort" -> sort.map(_.value),
        "order" -> order.map(_.value),
        "page" -> page.map(_.toString),
        "per_page" -> perPage.map(_.toString)
      ).collect {
        case (k, Some(v)) => k -> v
      }
    )
  }

  override def get(
      postNumber: Int,
      included: Set[PostIncluded] = Set.empty
  ): F[EsaResponse[Post]] = {
    httpClient.get(s"/v1/teams/$team/posts/$postNumber")
  }

  override def create(
      name: String,
      bodyMd: Option[String] = None,
      tags: List[String] = Nil,
      category: Option[String] = None,
      wip: Option[Boolean] = None,
      message: Option[String] = None,
      user: Option[String] = None
  ): F[EsaResponse[Post]] = {
    httpClient.post(
      path = s"/v1/teams/$team/posts",
      payload = PostPayload(
        CreatePost(
          name = name,
          bodyMd = bodyMd,
          tags = tags,
          category = category,
          wip = wip,
          message = message,
          user = user
        )
      )
    )
  }

  override def update(
      postNumber: Int,
      name: Option[String] = None,
      bodyMd: Option[String] = None,
      tags: List[String] = Nil,
      category: Option[String] = None,
      wip: Option[Boolean] = None,
      message: Option[String] = None,
      createdBy: Option[String] = None,
      updatedBy: Option[String] = None,
      originalRevision: Option[Revision] = None
  ): F[EsaResponse[Post]] = {
    httpClient.patch(
      path = s"/v1/teams/$team/posts/$postNumber",
      payload = PostPayload(
        UpdatePost(
          name = name,
          bodyMd = bodyMd,
          tags = tags,
          category = category,
          wip = wip,
          message = message,
          createdBy = createdBy,
          updatedBy = updatedBy,
          originalRevision = originalRevision
        )
      )
    )
  }

  override def delete(
      postNumber: Int
  ): F[EsaResponse[Unit]] = {
    httpClient.delete(s"/v1/teams/$team/posts/$postNumber")
  }
}

object EsaPosts {

  sealed trait PostParameter

  @ConfiguredJsonCodec case class CreatePost(
      name: String,
      bodyMd: Option[String] = None,
      tags: List[String] = Nil,
      category: Option[String] = None,
      wip: Option[Boolean] = None,
      message: Option[String] = None,
      user: Option[String] = None
  ) extends PostParameter

  @ConfiguredJsonCodec case class UpdatePost(
      name: Option[String] = None,
      bodyMd: Option[String] = None,
      tags: List[String] = Nil,
      category: Option[String] = None,
      wip: Option[Boolean] = None,
      message: Option[String] = None,
      createdBy: Option[String] = None,
      updatedBy: Option[String] = None,
      originalRevision: Option[Revision] = None
  ) extends PostParameter

  @ConfiguredJsonCodec case class PostPayload[A <: PostParameter](
      post: A
  )

}