'use strict';
var module = angular.module('services', []);
module.service('addressService', ['$http', '$q', function ($http, $q) {
    this.listByKeyword = function (page, size, keyword) {
        var params ={
            page: page,
            pageSize: size,
            keyword: keyword
        };
        return $http({ method: 'GET', url: '/v1/address/list', params: params });
    }

}
]);