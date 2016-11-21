package models.dao

import models.Temperature

trait TemperatureDao {

  def add(temperature: Temperature): String

  def findAll: List[Temperature]
}
