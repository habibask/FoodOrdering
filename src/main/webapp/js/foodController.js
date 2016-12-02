app.controller('FoodApplicationController', ['$scope', '$http', '$location', function($scope, $http, $location) {
    $scope.register = {}
    $scope.register.type = "customer"
    $scope.currentUser = {};
    $scope.restaurants = [];
    $scope.currentRest = {};
    $scope.userType;

    $scope.statuses = ['Received', 'Processing', 'Complete']
    $scope.cuisines = ['CHINESE', 'INDIAN', 'THAI', 'ITALIAN', 'MEXICAN', 'AMERICAN']

    $scope.Register = function(register) {
        var url = ''
        console.log(register)
        if (register.type == 'restaurant')
            url = 'http://localhost:8080/foodapp/restregister'
        else
            url = 'http://localhost:8080/foodapp/custregister'
        $http.post(url, register)
            .success(function(response) {
                console.log(response)
                if (response) {
                    $scope.currentUser = response;
                    console.log($scope.currentUser.orders)
                    if ($scope.currentUser.orders === undefined)
                        $scope.currentUser.orders = []
                    $scope.userType = register.type == 'restaurant' ? 'restaurant' : 'customer'
                    if (register.type == 'restaurant')
                        $location.url("/account")
                    else
                        $location.url("/search");
                } else {

                    $scope.message = "Unable to register. Check information entered!";
                }
            });
    }

    $scope.Login = function(input) {
        var url = ''
        console.log(input.restaurant)
        if (!input.restaurant)
            url = 'http://localhost:8080/foodapp/custlogin'
        else
            url = 'http://localhost:8080/foodapp/restlogin'

        $http.post(url, input)
            .success(function(response) {
                console.log(response)
                if (response) {
                    $scope.currentUser = response;
                    if ($scope.currentUser.orders === undefined)
                        $scope.currentUser.orders = []
                    $scope.userType = input.restaurant ? 'restaurant' : 'customer'
                    if (input.restaurant)
                        $location.url("/orders")
                    else
                        $location.url("/search");
                } else {
                    $scope.loginmessage = "Username/password do not match";
                }
            });
    }

    $scope.updateInfo = function(currentUser) {
        var url = ''
        console.log(currentUser)
        if ($scope.userType == 'restaurant')
            url = 'http://localhost:8080/foodapp/restupdate'
        else
            url = 'http://localhost:8080/foodapp/custupdate'
        $http.post(url, currentUser)
            .success(function(response) {
                console.log(response)
                if (response == "success") {
                    $scope.updatemessage = "Updated details";
                } else {
                    $scope.updatemessage = "Update failed";
                }
            });
    }

    $scope.signout = function() {
        $scope.currentUser = {};
        $scope.restaurants = [];
        $scope.currentRest = {};
        $scope.message = ""
        $scope.loginmessage = ""
        $location.url("/login");

    }

    $scope.search = function(filter) {
        $scope.searchErrorMessage = ""
        if (filter === undefined)
            filter = ""
        console.log("Searching rest " + filter)
        $http.get('http://localhost:8080/foodapp/search?filter=' + filter)
            .success(function(response) {
                if (response.length > 0) {
                    console.log(response)
                    $scope.restaurants = response;
                    $location.url("/restlist");
                } else {
                    $scope.searchErrorMessage = "No results found";
                }
            });

    }

    $scope.loadInfo = function(idx) {
        $scope.currentRest = $scope.restaurants[idx];
        console.log($scope.currentRest);
        $location.url("/restinfo");
    }

    $scope.updateCart = function(idx, quantity) {

        $scope.currentRest.menuItems[idx].quantity = quantity;
        console.log("Added " + quantity + "quantity to " + $scope.currentRest.menuItems[idx])
        console.log($scope.currentRest)
    }

    $scope.checkOut = function() {
        $location.url("/checkout");
        $scope.orderTotal = 0.0;
        items = $scope.currentRest.menuItems
        for (i in items) {
            if (items[i].quantity != 0)
                $scope.orderTotal += Number(items[i].quantity) * items[i].cost;
        }
    }

    $scope.submitOrder = function() {
        order = {}
        order.customerId = $scope.currentUser.id;
        order.restaurantName = $scope.currentRest.name;
        order.time = Date.now()
        order.restaurantId = $scope.currentRest.id;
        order.totalCost = $scope.orderTotal;
        order.status = $scope.statuses[0]
        order.foodItems = [];
        items = $scope.currentRest.menuItems
        for (i in items) {
            if (items[i].quantity != 0)
                order.foodItems.push({
                    id: items[i].id,
                    name: items[i].name,
                    cost: items[i].cost,
                    quantity: items[i].quantity
                })
        }
        console.log(order)
        $http.post('http://localhost:8080/foodapp/submit', order)
            .success(function(response) {
                if (response === "success") {
                    console.log(response)
                    $scope.currentUser.orders.push(order);
                    $location.url("/orders");
                    $scope.checkoutMessage = "Order successfully placed";
                } else {
                    $scope.checkoutErrorMessage = "Order could not be placed";
                }
            });
    }

    $scope.updateStatus = function(idx, status) {
        console.log("In updateStatus " + $scope.currentUser.orders[idx].status);
        $http.post('http://localhost:8080/foodapp/updateorder', $scope.currentUser.orders[idx])
            .success(function(response) {
                if (response === "success") {
                    console.log("In updateStatus " + $scope.currentUser.orders[idx].status);
                    console.log("status updated")
                    $scope.updateOrderStatus = true;
                } else {
                    $scope.updateOrderStatus = false;
                }
            });
    }

    $scope.editItem = function(item) {
        console.log(item)
        $scope.menuinput = item

    }

    $scope.update = function(item) {
        console.log("Adding new Item")
        $http.post('http://localhost:8080/foodapp/updateitem?rest=' + $scope.currentUser.id, item)
            .success(function(response) {
                if (response === "success") {
                    $scope.menuinput = {};
                    $scope.updateStatus = "Successful update";
                } else {
                    $scope.updateStatus = "Error updating";
                }
            });
    }

    $scope.deleteItem = function(item, idx) {
        console.log("Deleting an Item")
        $http.post('http://localhost:8080/foodapp/deleteitem?rest=' + $scope.currentUser.id, item)
            .success(function(response) {
                if (response === "success") {
                    $scope.currentUser.menuItems.splice(idx, 1);
                    $scope.updateStatus = "Successful delete";
                } else {
                    $scope.updateStatus = "Error deleting";
                }
            });
    }

    $scope.add = function(item) {
        $http.post('http://localhost:8080/foodapp/additem?rest=' + $scope.currentUser.id, item)
            .success(function(response) {
                if (response) {
                    console.log(response)
                    $scope.menuinput = {};
                    $scope.currentUser.menuItems.push(response);
                    $scope.updateStatus = "Successful add";
                } else {
                    $scope.updateStatus = "Error adding";
                }
            });
    }

    $scope.addReview = function(input) {
        input.customerId = $scope.currentUser.id;
        input.customerName = $scope.currentUser.name;
        input.rating = parseInt(input.rating)
        console.log(input)
        $http.post('http://localhost:8080/foodapp/addreview?rest=' + $scope.currentRest.id, input)
            .success(function(response) {
                if (response == "success") {
                    console.log(response)
                    $scope.currentRest.reviews.push(input);
                    $scope.reviewStatus = "Success";
                } else {
                    $scope.reviewStatus = "Error";
                }
            });

    }

    $scope.deleteUser = function(currentUser) {
        var url = ''
        console.log(currentUser)
        if ($scope.userType == 'restaurant')
            url = 'http://localhost:8080/foodapp/restdelete'
        else
            url = 'http://localhost:8080/foodapp/custdelete'
        $http.post(url, currentUser)
            .success(function(response) {
                console.log(response)
                if (response == "success") {
                    console.log(response)
                    $scope.signout();
                } else {
                    $scope.updatemessage = "Delete failed";
                }
            });
    }

}]);