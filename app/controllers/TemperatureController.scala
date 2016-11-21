package controllers

import com.google.inject.{Inject, Singleton}
import models.Temperature
import play.api.mvc.{Action, Controller}
import models.dao.TemperatureDao
import play.api.libs.json.{JsSuccess, Json}

@Singleton
class TemperatureController @Inject()(temperatureDao: TemperatureDao) extends Controller {

  def add = Action(parse.json) { request =>
    Json.fromJson[Temperature](request.body) match {
      case JsSuccess(temperature, _) => Ok(Json.toJson(temperatureDao.add(temperature)))
    }
  }

  def find(id: String) = TODO

  def findAll = Action { request =>
    Ok(Json.toJson(temperatureDao.findAll))
  }

  def remove(id: String) = TODO

}
