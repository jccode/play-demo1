import com.google.inject.AbstractModule
import javax.inject.Inject
import play.api.{Configuration, Environment}
import play.api.inject.ApplicationLifecycle
import repos.UserRepo

import scala.concurrent.Future

class Module(environment: Environment,
             configuration: Configuration) extends AbstractModule {
  override def configure(): Unit = {

  }
}

class UserRepoHook @Inject()(dao: UserRepo, lifecycle: ApplicationLifecycle) {
  lifecycle.addStopHook { () =>
    Future.successful(dao.close)
  }
}