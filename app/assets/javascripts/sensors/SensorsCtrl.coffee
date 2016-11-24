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
    dates = []
    values = []

    @SensorsService.getTemperatures(@sensor.sensorId)
      .then(
        (data) =>
          @$log.debug "Promise returned #{data.length} Temperatures"
          angular.forEach(data.slice(-20),
            (temperature) ->
              tmpDate = new Date(temperature.date);
              tmpDate = tmpDate.getDate() + '/' + (tmpDate.getMonth() + 1) + '/' + tmpDate.getFullYear();
              dates.push(tmpDate)
              values.push(temperature.value)
          )

          @$log.debug "showing temperatures of #{@sensor.sensorId}"

          Highcharts.chart('chart', {
            title: {
              text: 'Temperatures chart'
            }
            xAxis: {
              categories: dates,
            },
            series: [{
              name: sensor.sensorId,
              data: values
            }]
          });
      )

controllersModule.controller('SensorsCtrl', ['$log', 'SensorsService', SensorsCtrl])