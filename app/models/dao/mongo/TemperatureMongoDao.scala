package models.dao.mongo

import com.google.inject.Inject
import com.typesafe.config.ConfigFactory
import models.Temperature
import models.dao.TemperatureDao
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class TemperatureMongoDao @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends TemperatureDao {

  private val connectionTimeout = Duration.create(ConfigFactory.load().getString("mongodb.connectionTimeout"))
  val collection = Await.result(reactiveMongoApi.database, connectionTimeout).collection[JSONCollection]("temperatures")

  implicit val temperatureFormat = Json.format[Temperature]

  override def findAll: List[Temperature] = {
    val cursor = collection.find(Json.obj()).cursor[Temperature]()
    val futureList = cursor.collect[List]()
    Await.result(futureList, connectionTimeout)
  }

}
