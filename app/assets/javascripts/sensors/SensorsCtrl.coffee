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

  listTemperatures: (@sensor) ->
    id = @sensor.sensorId
    dates = []
    values = []
    angular.forEach(@sensor.temperatures,
      (temperature) ->
        tmpDate = new Date(temperature.date);
        tmpDate = tmpDate.getDate() + '/' + (tmpDate.getMonth() + 1) + '/' + tmpDate.getFullYear();
        dates.push(tmpDate)
        values.push(temperature.value)
    )

    @$log.debug "showing temperatures of #{id}"

    Highcharts.chart('chart', {
      title: {
        text: 'Temperatures chart'
      }
      xAxis: {
        categories: dates,
      },
      series: [{
        name: id,
        data: values
      }]
    });

controllersModule.controller('SensorsCtrl', ['$log', 'SensorsService', SensorsCtrl])