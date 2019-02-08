package esa4s.algebra

import esa4s._, esa4s.model._
import Posts._

trait PostsAlgebra[F[_]] {
  def list(
      query: Option[String] = None,
      included: Set[PostIncluded] = Set.empty,
      sort: Option[Sort] = None,
      order: Option[Order] = None,
      page: Option[Int] = None,
      perPage: Option[Int] = None
  ): F[EsaResponse[Posts]]

  def get(
      postNumber: Int,
      included: Set[PostIncluded] = Set.empty
  ): F[EsaResponse[Post]]

  def create(
      name: String,
      bodyMd: Option[String] = None,
      tags: List[String] = Nil,
      category: Option[String] = None,
      wip: Option[Boolean] = None,
      message: Option[String] = None,
      user: Option[String] = None
  ): F[EsaResponse[Post]]

  def update(
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
  ): F[EsaResponse[Post]]

  def delete(
      postNumber: Int
  ): F[EsaResponse[Unit]]
}

