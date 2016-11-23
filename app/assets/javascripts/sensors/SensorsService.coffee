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

servicesModule.service('SensorsService', ['$log', '$http', '$q', SensorsService])