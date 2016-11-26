app.controller('FoodApplicationController', ['$scope', '$http', function ($scope, $http) {
    console.log("in controller")
     $http.get('http://localhost:8080/users/hello')
         .success(function(response){
                console.log("result")
                 $scope.test = response;
         })
}]);