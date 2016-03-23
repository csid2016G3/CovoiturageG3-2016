'use strict';

angular.module('covoiturageApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


