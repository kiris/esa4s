package esa4s.algebra

import esa4s._, esa4s.model._

trait MembersAlgebra[F[_]] {
  def list(page: Option[Int] = None, perPage: Option[Int] = None): F[EsaResponse[Members]]
}
