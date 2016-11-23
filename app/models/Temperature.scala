package models

import org.joda.time.DateTime
import play.api.libs.json.Json

case class Temperature(id: Option[String],
                       sensorId: Option[String],
                       date: Option[DateTime],
                       value: Int)

object Temperature {

  implicit val writes = Json.writes[Temperature]
  implicit val reads = Json.reads[Temperature]
}