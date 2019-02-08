package esa4s

import esa4s.api.{EsaPosts, HttpClient}

class EsaClient[F[_]](
    accessToken: String,
    team: String
)(implicit httpClient: HttpClient[F]) {
  // private lazy val httpClient = new HttpClient[F](accessToken, s"https://$currentTeam.$endPoint")

  lazy val posts = new EsaPosts[F](httpClient, team)
}



