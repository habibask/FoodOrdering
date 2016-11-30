app.controller('FoodApplicationController', ['$scope', '$http', '$location', function ($scope, $http, $location) {

     $scope.currentUser = {};
     $scope.restaurants = [];
     $scope.currentRest = {};
     $scope.userType;
     //$scope.history = [];

     $scope.statuses = ['Received','Processing', 'Complete']
     $scope.cuisines = ['CHINESE', 'INDIAN', 'THAI', 'ITALIAN', 'MEXICAN' ,'AMERICAN']

     $scope.Login = function(input){
         var url = ''
         console.log(input.restaurant)
         if(!input.restaurant)
            url = 'http://localhost:8080/foodapp/custlogin'
         else
            url = 'http://localhost:8080/foodapp/restlogin'

         $http.post(url, input)
         .success(function(response){
            console.log(response)
            if(response){
                $scope.currentUser = response;
                $scope.userType = input.restaurant?'restaurant':'customer'
                $location.url("/search");
            }else{
                $scope.message = "Username/password do not match";
            }
         });
     }

     $scope.signout = function(){
          $scope.currentUser = {};
          $scope.restaurants = [];
          $scope.currentRest = {};
          $location.url("/login");

     }

     $scope.search = function(filter){
        if(filter===undefined)
            filter=""
        console.log("Searching rest "+filter)
        $http.get('http://localhost:8080/foodapp/search?filter='+filter)
         .success(function(response){
            if(response.length!=0){
                console.log(response)
                $scope.restaurants = response;
                $location.url("/restlist");
            }else{
                $scope.searchErrorMessage = "No results found";
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
         order.restaurantName = $scope.currentRest.name;
         order.time = Date.now()
         order.restaurantId = $scope.currentRest.id;
         order.totalCost = $scope.orderTotal;
         order.foodItems = [];
         items = $scope.currentRest.menuItems
         for(i in items){
            if(items[i].quantity!=0)
                order.foodItems.push({id:items[i].id,name:items[i].name,cost:items[i].cost,quantity:items[i].quantity})
         }
         console.log(order)
         $http.post('http://localhost:8080/foodapp/submit',order)
          .success(function(response){
             if(response==="success"){
                 console.log(response)
                 $scope.currentUser.orders.push(order);
                 $location.url("/history");
                 $scope.checkoutMessage = "Order successfully placed";
             }else{
                 $scope.checkoutErrorMessage = "Order could not be placed";
             }
          });
     }

     $scope.updateStatus = function(idx){
        console.log("In updateStatus "+$scope.currentUser.orders[idx].status);
        $http.post('http://localhost:8080/foodapp/updateorder',$scope.currentUser.orders[idx])
         .success(function(response){
            if(response==="success"){
                $scope.updateStatus = true;
            }else{
                $scope.updateStatus = false;
            }
         });
     }

     $scope.editItem = function(item){
        console.log(item)
        $scope.input = item

     }

     $scope.update = function(item){
        console.log("Adding new Item")
        $http.post('http://localhost:8080/foodapp/updateitem?rest='+$scope.currentUser.id,item)
          .success(function(response){
             if(response==="success"){
                 $scope.input = {};
                 $scope.updateStatus = "Successful update";
             }else{
                 $scope.updateStatus = "Error updating";
             }
          });
     }

     $scope.add = function(item){
         $http.post('http://localhost:8080/foodapp/additem?rest='+$scope.currentUser.id,item)
           .success(function(response){
              if(response!=null){
                  console.log(response)
                  $scope.currentUser.menuItems.push(response);
                  $scope.updateStatus = "Successful add";
                  $scope.input = {};
              }else{
                  $scope.updateStatus = "Error adding";
              }
           });
      }

}]);