angular.module('app',['ngRoute', 'ngAnimate', 'ngSanitize', 'errors', 'ui.bootstrap', 'bw.paging', 'services'])
    .config(function ($locationProvider, $routeProvider) {

        $routeProvider.when('/', {
            controller: 'mainController',
            templateUrl: '/views/main.html',
            controllerAs: 'mc'
        });
        $routeProvider.otherwise({
            controller: 'ErrorsController',
            templateUrl: '/views/errors.html'
        });
    }
);