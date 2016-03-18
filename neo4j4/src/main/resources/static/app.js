angular.module('mainApp', ['ngRoute'])

.config(['$httpProvider', '$routeProvider', function($httpProvider, $routeProvider) {
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    $routeProvider.
        when('/home', {
            templateUrl: 'home.html'
        }).
        when('/catalog', {
            templateUrl: 'catalog.html',
            controller: 'catalog'
        }).
        when('/contact', {
            templateUrl: 'contact.html'
        }).
        when('/about', {
            templateUrl: 'about.html'
        }).
        otherwise({
            redirectTo: '/home'
        });
}])

.controller('navigation', ['$scope', '$location', function($scope, $location) {
    $scope.isActive = function (menuItem) {
        return $location.path() == ('/' + menuItem.id);
    };

    $scope.menuClicked = function (menuItem) {
        $scope.showMenu = false;
    };
}])

.controller('catalog', ['$scope', '$http', function($scope, $http) {

    $scope.init = function() {
        $http.get('/rootCategory').success(function(category) {
            $scope.category = category;
            $scope.expand(category);
        });
    };

    $scope.expand = function (category) {
        if (!category.loaded) {
            $http.get(category._links.subCategories.href)
            .then(function(response) {
                category.subCategories = response.data._embedded.catalog;
                category.expanded = true;
                category.loaded = true;
            }, function(response) {
                category.expanded = true;
            });
        } else {
            category.expanded = true;
        }
    };

    $scope.collapse = function (category) {
        category.expanded = false;
    };

    $scope.init();
}]);