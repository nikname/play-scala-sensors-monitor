class SensorsCtrl

  constructor: (@$log, @SensorsService) ->
    @$log.debug "constructing SensorController"
    @sensors = []
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

controllersModule.controller('SensorsCtrl', ['$log', 'SensorsService', SensorsCtrl])