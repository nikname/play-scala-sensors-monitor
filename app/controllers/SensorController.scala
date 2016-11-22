package controllers

import com.google.inject.{Inject, Singleton}
import models.Sensor
import models.dao.SensorDao
import play.api.libs.json.{JsSuccess, Json}
import play.api.mvc.{Action, Controller}

@Singleton
class SensorController @Inject()(sensorDao: SensorDao) extends Controller {

  def add = Action(parse.json) { request =>
    Json.fromJson[Sensor](request.body) match {
      case JsSuccess(sensor, _) => Ok(Json.toJson(sensorDao.add(sensor)))
    }
  }

  def find(id: String) = TODO

  def findAll = Action { request =>
    Ok(Json.toJson(sensorDao.findAll))
  }

  def remove(id: String) = TODO
}
