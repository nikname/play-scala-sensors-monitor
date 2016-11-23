dependencies = [
  'ngRoute',
  'myApp.routeConfig',
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