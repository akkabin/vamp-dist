

import akka.actor._
import io.vamp.core.container_driver.ContainerDriverBootstrap
import io.vamp.core.dictionary.DictionaryBootstrap
import io.vamp.core.operation.OperationBootstrap
import io.vamp.core.persistence.PersistenceBootstrap
import io.vamp.core.pulse_driver.PulseDriverBootstrap
import io.vamp.core.rest_api.RestApiBootstrap
import io.vamp.core.router_driver.RouterDriverBootstrap

import scala.language.{implicitConversions, postfixOps}

trait VampCore extends App {

  implicit val actorSystem = ActorSystem("vamp-core")

  def bootstrap = {
    List() :+
      PersistenceBootstrap :+
      DictionaryBootstrap :+
      ContainerDriverBootstrap :+
      RouterDriverBootstrap :+
      PulseDriverBootstrap :+
      OperationBootstrap :+
      RestApiBootstrap
  }

  Runtime.getRuntime.addShutdownHook(new Thread() {
    override def run() = {
      bootstrap.foreach(_.shutdown)
      actorSystem.shutdown()
    }
  })

  bootstrap.foreach(_.run)
}
