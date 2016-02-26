angular.module('mainApp', [])

.config(function($httpProvider) {
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
})

.controller('catalog', ['$scope', '$http', function($scope, $http) {

    $scope.init = function() {
        $http.get('/catalog/search/root').success(function(category) {
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