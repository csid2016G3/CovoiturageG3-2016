'use strict';

angular.module('covoiturageApp')
    .factory('CarDriver', function ($resource, DateUtils) {
        return $resource('api/carDrivers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
