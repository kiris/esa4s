package esa4s.api

import cats.effect.IO
import esa4s.{BaseSpec, EsaResponseValues, UnsuccessfulHttpRequest}

class EsaPostsSpec extends BaseSpec with EsaResponseValues {

  val httpClient = new HttpClient[IO]("")
  //val esaClient = new EsaClient[IO]()
  val esaPosts = new EsaPosts[IO](httpClient, "doc")

  "Posts >> get" should "return a post data." in {
    val response = esaPosts.get(100).unsafeRunSync()
    val result = response.result

    result.statusCode should equal (200)
    result.headers("Content-Type")(0) should equal("application/json; charset=utf-8")
    result.result.number should equal (100)
  }

  it should "return error for an invalid post id." in {
    val response = esaPosts.get(-1).unsafeRunSync()
    val exception = response.exception

    exception shouldBe a [UnsuccessfulHttpRequest]
    inside(exception) {
      case UnsuccessfulHttpRequest(response, statusCode, headers) =>
        statusCode should equal (404)
        headers("Content-Type")(0) should equal("application/json; charset=utf-8")
        response should equal ("""{"error":"not_found","message":"Not Found"}""")

    }
  }

  "Posts >> list" should "return a list of issues" in {
    val response = esaPosts.list(query = Some("wip:true")).unsafeRunSync()
    val result = response.result

    result.statusCode should equal (200)
    result.headers("Content-Type")(0) should equal("application/json; charset=utf-8")
    result.result.posts should not be empty
  }
}
