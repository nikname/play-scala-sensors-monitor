class SensorsCtrl

  constructor: (@$log, @SensorsService) ->
    @$log.debug "constructing SensorController"
    @sensors = []
    @temperatures = []
    @getAllSensors()

  getAllSensors: () ->
    @$log.debug "getAllSensors()"

    @SensorsService.listSensors()
      .then(
        (data) =>
          @$log.debug "Promise returned #{data.length} Sensors"
          @sensors = data
      ,
        (error) =>
          @$log.error "Unable to get Sensors: #{error}"
      )

  listTemperatures: (@sensor) ->
    id = @sensor.sensorId
    @$log.debug "showing temperatures of #{id}"
    @temperatures = @sensor.temperatures

controllersModule.controller('SensorsCtrl', ['$log', 'SensorsService', SensorsCtrl])