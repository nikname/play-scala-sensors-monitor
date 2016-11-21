package models.dao

import models.Temperature

trait TemperatureDao {

  def findAll: List[Temperature]
}
