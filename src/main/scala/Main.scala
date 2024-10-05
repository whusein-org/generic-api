import cats.effect._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.circe._
import io.circe.generic.auto._
import org.http4s.server.blaze.BlazeServerBuilder

object Main extends IOApp {

  // Case class to represent a greeting
  case class Greeting(message: String)

  // Circe entity encoder to automatically convert Scala case class to JSON
  implicit val greetingEncoder: EntityEncoder[IO, Greeting] = jsonEncoderOf[IO, Greeting]

  // Define the route
  val helloWorldService = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      // Create a greeting message with the provided name
      Ok(Greeting(s"Hello, $name!"))
  }

  // Create the HTTP server
  override def run(args: List[String]): IO[ExitCode] = {
    // Combine routes into a single HTTP app
    val httpApp = helloWorldService.orNotFound

    // Start the server
    BlazeServerBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(httpApp)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}