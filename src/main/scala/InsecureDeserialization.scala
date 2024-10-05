import scala.io.StdIn
import java.io._

case class User(username: String, password: String)

object InsecureDeserialization extends App {

  println("Enter serialized User object (base64 encoded):")
  val input = StdIn.readLine()

  // Insecure deserialization: directly deserializing user input
  val user = deserializeUser(input)

  println(s"User logged in: ${user.username}")

  def deserializeUser(serialized: String): User = {
    // Decoding base64 input
    val decodedBytes = java.util.Base64.getDecoder.decode(serialized)

    // Insecure deserialization: reading object from byte array
    val in = new ObjectInputStream(new ByteArrayInputStream(decodedBytes))
    in.readObject().asInstanceOf[User] // Vulnerability: Unsafe deserialization
  }
}