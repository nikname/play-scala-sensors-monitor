package models.dao.mongo

import com.google.inject.Inject
import com.typesafe.config.ConfigFactory
import models.Sensor
import models.dao.SensorDao
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection.JSONCollection

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class SensorMongoDao @Inject()(reactiveMongoApi: ReactiveMongoApi) extends SensorDao {

  private val connectionTimeout = Duration.create(ConfigFactory.load().getString("mongodb.connectionTimeout"))
  val collection = Await.result(reactiveMongoApi.database, connectionTimeout).collection[JSONCollection]("sensors")

  implicit val sensorFormat = Json.format[Sensor]

  override def findAll: List[Sensor] = {
    val cursor = collection.find(Json.obj()).cursor[Sensor]()
    val futureList = cursor.collect[List]()
    Await.result(futureList, connectionTimeout)
  }

}
