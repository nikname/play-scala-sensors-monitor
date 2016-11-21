package models

import org.joda.time.DateTime
import play.api.libs.json.Json

case class Temperature(sensorId: String,
                       date: DateTime,
                       measurement: Int)

object Temperature {

  implicit val writes = Json.writes[Temperature]
  implicit val reads = Json.reads[Temperature]
}