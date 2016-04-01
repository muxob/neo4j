angular.module('mainApp', ['ngRoute'])

.config(['$httpProvider', '$routeProvider', function($httpProvider, $routeProvider) {
    //$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    $routeProvider.
    when('/home', {
        templateUrl: 'templates/home.html'
    }).
    when('/catalog', {
        templateUrl: 'templates/catalog.html',
        controller: 'catalog'
    }).
    when('/contact', {
        templateUrl: 'templates/contact.html'
    }).
    when('/about', {
        templateUrl: 'templates/about.html'
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

.controller('catalog', ['$scope', '$rootScope', '$http', function($scope, $rootScope, $http) {

    $scope.init = function() {
        $http.get('/rootCategory').success(function(category) {
            $scope.category = category;
            $scope.expand(category);
        });
    };

    $scope.expand = function(category) {
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
        $scope.changeCategory(category);
    };

    $scope.collapse = function(category) {
        category.expanded = false;
        $scope.changeCategory(category);
    };

    $scope.changeCategory = function(category) {
        $rootScope.$emit('category_change', category);
    };

    $scope.createSubCategory = function(category) {
        $rootScope.$emit('create_subcategory', category);
    };

    $scope.deleteCategory = function(category) {
        $http({
            url: category._links.self.href,
            method: "DELETE"
        })
        .then(function(response) {
            $http.get(category._links.parent.href)
            .then(function(response) {
                category.subCategories = response.data._embedded.catalog;
                category.expanded = true;
                category.loaded = true;
            });
        });
    };

    $scope.init();
}])

.controller('products', ['$scope', '$rootScope', '$http', function($scope, $rootScope, $http) {
    $rootScope.$on('category_change',  function(e, category) {
        $scope.loadCategoryProducts(category);
        $scope.loadRecursiveCategoryProducts(category);
    });

    $scope.loadCategoryProducts = function(category) {
        $scope.catalogProducts = null;
        $http.get(category._links.products.href)
        .then(function(response) {
            $scope.catalogProducts = response.data._embedded.products;
        });
    };

    $scope.loadRecursiveCategoryProducts = function(category) {
        $scope.allProducts = null;
        $http({
            url: '/catalog/search/recursiveCategoryProducts',
            method: "GET",
            params: {id: category.nodeId}
        })
        .then(function(response) {
            $scope.allProducts = response.data._embedded.products;
        });
    };
}])

.controller('newCategory', ['$scope', '$rootScope', '$http', function($scope, $rootScope, $http) {
    $scope.showForm = false;

    $rootScope.$on('create_subcategory',  function(e, category) {
        $http({
            url: '/catalog/search/parentCategories',
            method: "GET",
            params: {id: category.nodeId}
        })
        .then(function(response) {
            $scope.parentCategories = [].concat(response.data._embedded.catalog.reverse()).concat(category);
            $scope.showForm = true;
        });
    });

    $scope.createNewCategory = function() {
        var parentCategory = this.parentCategories[this.parentCategories.length - 1],
         newCategory = {
            name: this.name,
            parent: {
                name: parentCategory.name
            },
            products: this.products ? this.products.split(/\n/).map(function(pName) { return {name: pName}; }) : null
        };

        $http({
            url: '/catalog',
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            data: newCategory
        })
        .then(function(response) {
            var parentSubs = parentCategory.subCategories;
            if (!parentSubs || !parentSubs instanceof Array) {
                parentSubs = parentCategory.subCategories = [];
            }
            parentSubs.push(response.data);
            $scope.showForm = false;
        });
    };
}])
;