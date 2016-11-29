app.controller('FoodApplicationController', ['$scope', '$http', '$location', function ($scope, $http, $location) {

     $scope.currentUser = {};
     $scope.restaurants = [];
     $scope.currentRest = {};
     $scope.history = [];

     $scope.input = {};
     $scope.Login = function(){
         $http.post('http://localhost:8080/foodapp/login',$scope.input)
         .success(function(response){
            if(response){
                console.log(response)
                $scope.currentUser = response;
                $location.url("/search");
            }else{
                $scope.message = "Username/password do not match";
            }
         });
     }

     $scope.search = function(filter){
        console.log("Searching rest "+filter)
        $http.get('http://localhost:8080/foodapp/search?filter='+filter)
         .success(function(response){
            if(response){
                console.log(response)
                $scope.restaurants = response;
                $location.url("/restlist");
            }else{
                $scope.searchMessage = "No results found";
            }
         });

     }

     $scope.loadInfo = function(idx){
        $scope.currentRest = $scope.restaurants[idx];
        console.log($scope.currentRest);
        $location.url("/restinfo");
     }

     $scope.updateCart = function(idx,quantity){

        $scope.currentRest.menuItems[idx].quantity = quantity;
        console.log("Added " + quantity + "quantity to " + $scope.currentRest.menuItems[idx])
        console.log($scope.currentRest)
     }

     $scope.checkOut = function(){
         $location.url("/checkout");
         $scope.orderTotal = 0.0;
         items = $scope.currentRest.menuItems
         for(i in items){
            if(items[i].quantity!=0)
                $scope.orderTotal += Number(items[i].quantity) * items[i].cost;
         }
     }

     $scope.submitOrder = function(){
         order = {}
         order.customerId = $scope.currentUser.id;
         order.restaurantId = $scope.currentRest.id;
         order.totalCost = $scope.orderTotal;
         order.foodItems = [];
         items = $scope.currentRest.menuItems
         for(i in items){
            if(items[i].quantity!=0)
                order.foodItems.push({id:items[i].id,cost:items[i].cost,quantity:items[i].quantity})
         }
         console.log(order)
         $http.post('http://localhost:8080/foodapp/submit',order)
          .success(function(response){
             if(response==="success"){
                 console.log(response)
                 $scope.history.push(response);
                 //$location.url("/history");
                 $scope.checkoutMessage = "Order successfully placed";
             }else{
                 $scope.checkoutErrorMessage = "Order could not be placed";
             }
          });
     }

}]);