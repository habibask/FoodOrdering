var app = angular.module('FoodApplication', ['ngResource','ngRoute']);
console.log("in service js")

//app.factory('UserFactory', function ($resource,$http) {
//    return $http.get('http://localhost:8080/users/hello')
//    .success(function(response){
//        console.log(response);
//    })
//});

app.controller('FoodApplicationController', ['$scope', '$http', function ($scope, $http) {
    console.log("in controller")
     $http.get('http://localhost:8080/users/hello')
         .success(function(response){
             $scope.test = response;
         })
}]);