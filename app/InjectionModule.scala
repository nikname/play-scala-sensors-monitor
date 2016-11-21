import com.google.inject.{AbstractModule, Singleton}
import models.dao.TemperatureDao
import models.dao.mongo.TemperatureMongoDao

class InjectionModule extends AbstractModule {

  def configure(): Unit = {
    bind(classOf[TemperatureDao]).to(classOf[TemperatureMongoDao]).in(classOf[Singleton])
  }
}
