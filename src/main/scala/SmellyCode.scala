object SmellyCode extends App {

  val flag = !false
  if(!flag) println("weird flag")

  def methodWithUnreachableCode: Boolean = {
    return true
    println("would never get printed")
    true
  }

  methodWithUnreachableCode
}
