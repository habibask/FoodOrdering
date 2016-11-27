app.controller('FoodApplicationController', ['$scope', '$http', function ($scope, $http) {
    console.log("in controller")

     $scope.input = {};
     $scope.Login = function(){

         console.log($scope.input);
         $http.post('http://localhost:8080/foodapp/login',$scope.input)
         .success(function(response){
            console.log(response)
            $scope.message = response.name;
         })
         .error(function(err){
            console.log(err);
         });
     }

}]);