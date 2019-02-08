package esa4s

import org.scalactic.source
import org.scalatest.exceptions.{StackDepthException, TestFailedException}

trait EsaResponseValues {

  import scala.language.implicitConversions

  implicit class EsaResponseValuable[A](res: EsaResponse[A])(implicit pos: source.Position) {
    def result: EsaResult[A] = {
      try {
        res.right.get
      } catch {
        case cause: NoSuchElementException =>
          throw new TestFailedException((_: StackDepthException) => Some(s"The EsaResponse on which result was invoked was not defined as a EsaResult. message=${res.left.get.getMessage}"), Some(cause), pos)
      }
    }

    def exception: EsaException = {
      try {
        res.left.get
      } catch {
        case cause: NoSuchElementException =>
          throw new TestFailedException((_: StackDepthException) => Some("The EsaResponse on which exception was invoked was not defined as a EsaException."), Some(cause), pos)
      }
    }
  }
}
