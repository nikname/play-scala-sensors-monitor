package models.dao.mongo

import com.google.inject.Inject
import com.typesafe.config.ConfigFactory
import models.Sensor
import models.dao.SensorDao
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.bson.{BSONDocument, BSONObjectID}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class SensorMongoDao @Inject()(reactiveMongoApi: ReactiveMongoApi) extends SensorDao {

  private val connectionTimeout = Duration.create(ConfigFactory.load().getString("mongodb.connectionTimeout"))
  val collection = Await.result(reactiveMongoApi.database, connectionTimeout).collection[JSONCollection]("sensors")

  implicit val sensorFormat = Json.format[Sensor]

  override def add(sensor: Sensor): String = {
    val document = sensorToDocument(sensor)

    val future = findSensor(sensor.name).flatMap {
      case None =>
        collection.insert(document).map {
          _ => document.getAs[BSONObjectID]("_id").get.stringify
        }
      case Some(s) =>
        val message = "Sensor \"" + s.name + "\" already exists"
        Logger.error(message)
        throw new Exception(message)
    }

    Await.result(future, connectionTimeout)
  }

  override def findAll: List[Sensor] = {
    val cursor = collection.find(Json.obj()).cursor[Sensor]()
    val futureList = cursor.collect[List]()
    Await.result(futureList, connectionTimeout)
  }

  private def findSensor(name: String) = this.collection
    .find(BSONDocument("name" -> name))
    .one[Sensor]

  private def sensorToDocument(sensor: Sensor): BSONDocument = BSONDocument(
    "_id" -> BSONObjectID.generate(),
    "name" -> sensor.name,
    "highTemperature" -> sensor.highTemperature,
    "criticalTemperature" -> sensor.criticalTemperature
  ) // TODO: Add given temperatures
}
