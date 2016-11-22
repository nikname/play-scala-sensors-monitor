package models.dao

import models.Sensor

trait SensorDao {

  def add(sensor: Sensor): String

  def find(id: String): Option[Sensor]

  def findAll: List[Sensor]

  def remove(id: String): Boolean
}
