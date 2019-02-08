package esa4s.algebra

import esa4s._, esa4s.model._

trait StatsAlgebra[F[_]] {
  def get(): F[EsaResponse[Stats]]
}
