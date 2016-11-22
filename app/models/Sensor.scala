package models

import play.api.libs.json.Json

case class Sensor(id: Option[String],
                  name: String,
                  temperatures: List[Temperature],
                  highTemperature: Option[Int],
                  criticTemperature: Option[Int])

object Sensor {

  implicit val writes = Json.writes[Sensor]
  implicit val reads = Json.reads[Sensor]
}
