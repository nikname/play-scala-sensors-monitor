package controllers

import com.google.inject.{Inject, Singleton}
import models.dao.SensorDao
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

@Singleton
class SensorController @Inject()(sensorDao: SensorDao) extends Controller {

  def add = TODO

  def find(id: String) = TODO

  def findAll = Action { request =>
    Ok(Json.toJson(sensorDao.findAll))
  }

  def remove(id: String) = TODO
}
