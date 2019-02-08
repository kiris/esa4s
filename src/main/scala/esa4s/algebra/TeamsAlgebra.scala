package esa4s.algebra

import esa4s._, esa4s.model._

trait TeamsAlgebra[F[_]] {
  def list(page: Option[Int] = None, perPage: Option[Int] = None): F[EsaResponse[Teams]]
  def get(name: String): F[EsaResponse[Team]]
}
