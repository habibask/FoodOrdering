var app = angular.module('FoodApplication', ['ngResource','ngRoute']);

app.config(['$routeProvider',
  function ($routeProvider) {
      $routeProvider.
        when('/home', {
            templateUrl: 'pages/login.html'
        }).
        when('/search', {
            templateUrl: 'pages/search.html'
        }).
        when('/restlist', {
            templateUrl: 'pages/restaurantList.html'
        }).
        when('/restinfo', {
            templateUrl: 'pages/restaurantInfo.html'
        }).
        when('/checkout', {
            templateUrl: 'pages/checkout.html'
        }).
        otherwise({
            redirectTo: '/home'
        });
}]);