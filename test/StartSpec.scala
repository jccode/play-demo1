import org.scalatestplus.play._

import scala.collection.mutable.ListBuffer


/**
  * DataSpec
  *
  * @author 01372461
  */
class StartSpec extends PlaySpec {

  "A Stack" must {
    "pop values in last-in-first-out order" in {
      var stack = ListBuffer.empty[Int]
      stack += 1
      stack += 2
      stack.last must be(2)
      stack.trimEnd(1)
      stack.last must be(1)
    }

    "throw NoSuchElementException if an empty stack is popped" in {
      var emptyStack = ListBuffer()
      a [NoSuchElementException] should be thrownBy {
        emptyStack.last
      }
    }
  }


}
