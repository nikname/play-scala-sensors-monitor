package models.dao.mongo

import com.google.inject.Inject
import com.typesafe.config.ConfigFactory
import models.Temperature
import models.dao.TemperatureDao
import org.joda.time.DateTime
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class TemperatureMongoDao @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends TemperatureDao {

  private val connectionTimeout = Duration.create(ConfigFactory.load().getString("mongodb.connectionTimeout"))
  val collection = Await.result(reactiveMongoApi.database, connectionTimeout).collection[JSONCollection]("temperatures")

  implicit val temperatureFormat = Json.format[Temperature]

  override def add(temperature: Temperature): String = {
    val document = temperatureToDocument(temperature)
    val future = collection.insert(document).map {
      _ => document.getAs[BSONObjectID]("_id").get.stringify
    }
    Await.result(future, connectionTimeout)
  }

  override def findAll: List[Temperature] = {
    val cursor = collection.find(Json.obj()).cursor[Temperature]()
    val futureList = cursor.collect[List]()
    Await.result(futureList, connectionTimeout)
  }

  private def temperatureToDocument(temperature: Temperature): BSONDocument = BSONDocument(
    "_id" -> BSONObjectID.generate(),
    "date" -> DateTime.now.getMillis,
    "value" -> temperature.value
  )
}