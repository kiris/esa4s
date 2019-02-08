package esa4s.algebra

import esa4s._, esa4s.model._

trait CommentsAlgebra[F[_]] {
  def list(postNumber: Int): F[EsaResponse[Comments]]

  def get(
      postNumber: Int,
      commentId: Int,
      included: List[CommentIncluded] = Nil
  ): F[EsaResponse[Comment]]

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
