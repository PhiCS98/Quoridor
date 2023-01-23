package util
import Quoridor.util.{Event, Observable, Observer}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ObservableSpec extends AnyFlatSpec with Matchers {
  "An Observable" should "add an Observer to its list of subscribers" in {
    val observable = new Observable {}
    val observer = new Observer { override def update(e: Event): Unit = {} }
    observable.add(observer)
    observable.getSubscribers() should contain(observer)
  }

  it should "remove an Observer from its list of subscribers" in {
    val observable = new Observable {}
    val observer = new Observer { override def update(e: Event): Unit = {} }
    observable.add(observer)
    observable.remove(observer)
    observable.getSubscribers() should not contain observer
  }

  it should "notify all its subscribers when an event occurs" in {
    val observable = new Observable {}
    val observer = new Observer { override def update(e: Event): Unit = {} }
    val observer2 = new Observer { override def update(e: Event): Unit = {} }
    observable.add(observer)
    observable.add(observer2)
    var notified = false
    var notified2 = false
    observable.notifyObservers(Event.FIELDCHANGED)
    notified = true
    notified2 = true
    observable.notifyObservers(Event.FIELDCHANGED)
    notified shouldBe true
    notified2 shouldBe true
  }
}
