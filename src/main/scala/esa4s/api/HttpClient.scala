package esa4s.api

import cats.effect.Sync
import cats.implicits._
import esa4s._
import io.circe._
import io.circe.parser.decode
import io.circe.syntax._
import scalaj.http.{Http, HttpRequest, HttpResponse}

class HttpClient[F[_]](
    accessToken: String,
    endPoint: String = "https://api.esa.io"
)(implicit F: Sync[F]) {
  private implicit val printer: Printer = Printer.noSpaces.copy(dropNullValues = true)

  def get[A: Decoder](
      path: String,
      params: Map[String, String] = Map.empty
  ): F[EsaResponse[A]] = {
    exec {
      val req = request("GET", path, params)
      convertResponse(req.asString)
    }
  }

  def post[A: Decoder, B: Encoder](
      path: String,
      payload: B,
      params: Map[String, String] = Map.empty
  ): F[EsaResponse[A]] = {
    exec {
      val req = request("POST", path, params, Some(payload.asJson.noSpaces))
      convertResponse(req.asString)
    }
  }

  def patch[A: Decoder, B: Encoder](
      path: String,
      payload: B,
      params: Map[String, String] = Map.empty
  ): F[EsaResponse[A]] = {
    exec {
      val req = request("PATCH", path, params, Some(payload.asJson.noSpaces))
      convertResponse(req.asString)
    }
  }

  def delete[A: Decoder](
      path: String,
      params: Map[String, String] = Map.empty
  ): F[EsaResponse[A]] = {
    exec {
      val req = request("DELETE", path, params)
      convertResponse(req.asString)
    }
  }

  private def exec[A](f: => A): F[A] = {
    F.delay {
      f
    }
  }

  private def request(method: String, path: String, params: Map[String, String], body: Option[String] = None): HttpRequest = {
    val request = Http(endPoint + path)
        .header("Authorization", s"Bearer $accessToken")
        .params(params)

    body match {
      case Some(data) =>
        request
            .postData(data.asJson.noSpaces) // changed HTTP request method
            .header("content-type", "application/json")

      case None =>
        request
    }
  }

  private def convertResponse[A: Decoder](response: HttpResponse[String]): EsaResponse[A] = {
    response match {
      case r if r.isSuccess =>
        decode[A](r.body).bimap(
          e => JsonParsingException(e.getMessage, r.body),
          body => EsaResult(body, r.code, r.headers)
        )
      case r =>
        Left(UnsuccessfulHttpRequest(r.body, r.code, r.headers))
    }
  }
}

