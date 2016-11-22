package models.dao

import models.Sensor

trait SensorDao {

  def findAll: List[Sensor]
}
