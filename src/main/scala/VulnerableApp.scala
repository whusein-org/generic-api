import java.sql.{Connection, DriverManager, ResultSet}

object VulnerableApp {

  // Hardcoded sensitive information (SAST tools will flag this)
  val dbUrl = "jdbc:mysql://localhost:3306/mydb"
  val dbUser = "admin"           // Hardcoded username
  val dbPassword = "password123"  // Hardcoded password

  // Insecure SQL query construction (vulnerable to SQL injection)
  def authenticateUser(username: String, password: String): Boolean = {
    // Establish connection to the database
    val conn: Connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)

    // Vulnerable SQL query (unsanitized user input directly in the query)
    val query = s"SELECT * FROM users WHERE username = '$username' AND password = '$password'"

    // Execute the query
    val stmt = conn.createStatement()
    val rs: ResultSet = stmt.executeQuery(query)

    // Check if a matching user is found
    rs.next()
  }

  def main(args: Array[String]): Unit = {
    // Simulated user input (this can be exploited with SQL injection)
    val username = "admin' --"
    val password = "doesn't matter"

    // Authenticate user with vulnerable method
    if (authenticateUser(username, password)) {
      println("User authenticated successfully")
    } else {
      println("Authentication failed")
    }
  }
}