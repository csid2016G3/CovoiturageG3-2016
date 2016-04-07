'use strict';

angular.module('covoiturageApp')
    .factory('Driver', function ($resource, DateUtils) {
        return $resource('api/drivers/:id', {}, {
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
