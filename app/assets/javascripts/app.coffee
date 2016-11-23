dependencies = [
  'ngRoute',
  'myApp.routeConfig',
  'myApp.controllers',
  'myApp.services'
]

app = angular.module('myApp', dependencies)

angular.module('myApp.routeConfig', ['ngRoute'])
  .config([
    '$routeProvider',
    ($routeProvider) ->
      $routeProvider
        .otherwise({redirectTo: '/'})
  ])
  .config([
    '$locationProvider',
    ($locationProvider) ->
      $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
      })
  ])

@controllersModule = angular.module('myApp.controllers', [])
@servicesModule = angular.module('myApp.services', [])