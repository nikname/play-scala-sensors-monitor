package models.dao.mongo

import com.google.inject.Inject
import com.typesafe.config.ConfigFactory
import models.Temperature
import models.dao.TemperatureDao
import org.joda.time.DateTime
import play.api.Logger
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
  lazy val collection = Await.result(reactiveMongoApi.database, connectionTimeout).collection[JSONCollection]("sensors")

  implicit val temperatureFormat = Json.format[Temperature]

  override def add(temperature: Temperature): Boolean = {
    val selector = BSONDocument("sensorId" -> temperature.sensorId)
    val modifier = BSONDocument("temperatures" -> temperatureToDocument(temperature))
    val future = collection.update(selector, BSONDocument("$push" -> modifier))

    Await.result(future, connectionTimeout) match {
      case res if res.nModified == 1 => true
      case _ =>
        val message = "There is no sensor \"" + temperature.sensorId + "\""
        Logger.error(message)
        throw new Exception(message)
    }
  }

  override def findAll: List[Temperature] = {
    val cursor = collection.find(Json.obj()).cursor[Temperature]()
    val futureList = cursor.collect[List]()
    Await.result(futureList, connectionTimeout)
  }

  private def temperatureToDocument(temperature: Temperature): BSONDocument = BSONDocument(
    "date" -> temperature.date.getOrElse(DateTime.now).getMillis,
    "value" -> temperature.value
  )
}