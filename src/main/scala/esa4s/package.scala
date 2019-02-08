package object esa4s {

  type EsaResponse[A] = Either[EsaException, EsaResult[A]]

  case class EsaResult[A](
      result: A,
      statusCode: Int,
      headers: Map[String, IndexedSeq[String]]
  )

  sealed abstract class EsaException(
      msg: String,
      cause: Option[Throwable] = None
  ) extends Throwable(msg) {
    cause foreach initCause
  }

  case class JsonParsingException(
      msg: String,
      body: String
  ) extends EsaException(msg)

  case class UnsuccessfulHttpRequest(
      response: String,
      statusCode: Int,
      headers: Map[String, IndexedSeq[String]]
  ) extends EsaException(response)

}
