package controllers

import com.google.inject.{Inject, Singleton}
import play.api.mvc.{Action, Controller}
import models.dao.TemperatureDao
import play.api.libs.json.Json

@Singleton
class TemperatureController @Inject()(temperatureDao: TemperatureDao) extends Controller {

  def add = TODO

  def find(id: String) = TODO

  def findAll = Action { request =>
    Ok(Json.toJson(temperatureDao.findAll))
  }

  def remove(id: String) = TODO

}
