class SensorsService

  @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
  @defaultConfig = {headers: @headers}

  constructor: (@$log, @$http, @$q) ->
    @$log.debug "constructing SensorsService"

  listSensors: () ->
    @$log.debug "listSensors()"
    deferred = @$q.defer()

    @$http.get("/sensors")
      .success((data, status, headers) =>
        @$log.info("Successfully listed Sensors - status #{status}")
        deferred.resolve(data)
      )
      .error((data, status, headers) =>
        @$log.error("Failed to list Sensors - status #{status}")
        deferred.reject(data)
      )
    deferred.promise

  getSensor: (sensorId) ->
    @$log.debug "getSensor(#{sensorId})"
    deferred = @$q.defer()

    @$http.get("/sensors/#{sensorId}")
      .success((data, status, headers) =>
        @$log.info("Successfully retrieved Sensor - status #{status}")
        deferred.resolve(data)
      )
      .error((data, status, headers) =>
        @$log.error("Failed to retrieve Sensor - status #{status}")
        deferred.reject(data)
      )
    deferred.promise

  getTemperatures: (sensorId) ->
    @$log.debug "getTemperatures(#{sensorId})"
    deferred = @$q.defer()

    @$http.get("/temperatures/#{sensorId}")
      .success((data, status, headers) =>
        @$log.info("Successfully retrieved Temperatures - status #{status} - #{data.length}")
        deferred.resolve(data)
      )
      .error((data, status, headers) =>
        @$log.error("Failed to retrieve Temperatures - status #{status}")
        deferred.reject(data)
      )
    deferred.promise

servicesModule.service('SensorsService', ['$log', '$http', '$q', SensorsService])