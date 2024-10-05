object VulnerablePassword {
  def main(args: Array[String]): Unit = {
    // Hardcoded sensitive information
    val dbPassword: String = "SuperSecretPassword123" // Vulnerability: Hardcoded password

    // Simulating a database connection
    connectToDatabase("username", dbPassword)

    println("Connected to the database successfully!")
  }

  def connectToDatabase(username: String, password: String): Unit = {
    // Simulated database connection logic
    println(s"Connecting to the database with user: $username and password: $password")
  }
}