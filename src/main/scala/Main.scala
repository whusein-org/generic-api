import cats.effect.*
import cats.implicits.toSemigroupKOps
import io.circe.Encoder
import org.http4s.*
import org.http4s.dsl.io.*
import org.http4s.circe.*
import io.circe.generic.auto.*
import org.http4s.server.blaze.BlazeServerBuilder

object Main extends IOApp {

  // Case class to represent a greeting
  case class Greeting(message: String)

  // Circe entity encoder to automatically convert Scala case class to JSON
  implicit val greetingEncoder: Encoder[Greeting] = Encoder.forProduct1("message")(_.message)

  // Circe-aware entity encoder for HTTP4s
  implicit val greetingEntityEncoder: EntityEncoder[IO, Greeting] = jsonEncoderOf[IO, Greeting]


  // Define the route
  val helloWorldService = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      // Create a greeting message with the provided name
      Ok(Greeting(s"Hello, $name!"))
  }

  // Simulate a database interaction with potential SQL injection vulnerability
  def getUserFromDB(username: String): String = {
    // This is an example of bad practice. Concatenating user input directly into a query.
    val query = s"SELECT * FROM users WHERE username = '$username'"
    // Simulated query execution (in reality, this would query a database)
    s"Executing query: $query"
  }

  // Define the vulnerable route
  val vulnerableService = HttpRoutes.of[IO] {
    case GET -> Root / "vulnerable" / username =>
      // Return a response showing the constructed SQL query (simulating vulnerability)
      Ok(getUserFromDB(username))
  }

  // Create the HTTP server
  override def run(args: List[String]): IO[ExitCode] = {
    // Combine routes into a single HTTP app
    val httpApp = (helloWorldService <+> vulnerableService).orNotFound

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