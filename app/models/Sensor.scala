package models

import play.api.libs.json.Json

case class Sensor(id: Option[String],
                  sensorId: String,
                  name: Option[String],
                  temperatures: Option[List[Temperature]],
                  highTemperature: Option[Int],
                  criticalTemperature: Option[Int])

object Sensor {

  implicit val writes = Json.writes[Sensor]
  implicit val reads = Json.reads[Sensor]
}
