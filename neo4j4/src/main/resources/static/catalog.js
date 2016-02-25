angular.module('mainApp', [])

.config(function($httpProvider) {
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
})

.controller('catalog', ['$scope', '$http', function($scope, $http) {

    $scope.category = null;

    $scope.init = function() {
        $http.get('/catalog/search/root').success(function(category) {
            $scope.category = category;
            $scope.fetchSubCategories(category);
        });
    };

    $scope.fetchSubCategories = function (category) {
      $http.get(category._links.subCategories.href).success(function(data) {
          category.subCategories = data._embedded.catalog;
      });
    }

    $scope.init();
}]);