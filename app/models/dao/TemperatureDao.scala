package models.dao

import models.Temperature

trait TemperatureDao {

  def add(temperature: Temperature): Boolean

  def findAll(sensorId: String): List[Temperature]
}
