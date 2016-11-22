package models.dao

import models.Sensor

trait SensorDao {

  def add(sensor: Sensor): String

  def findAll: List[Sensor]
}
