import com.google.inject.{AbstractModule, Singleton}
import models.dao.{SensorDao, TemperatureDao}
import models.dao.mongo.{SensorMongoDao, TemperatureMongoDao}

class InjectionModule extends AbstractModule {

  def configure(): Unit = {
    bind(classOf[TemperatureDao]).to(classOf[TemperatureMongoDao]).in(classOf[Singleton])
    bind(classOf[SensorDao]).to(classOf[SensorMongoDao]).in(classOf[Singleton])
  }
}
