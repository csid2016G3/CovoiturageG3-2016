 'use strict';

angular.module('covoiturageApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-covoiturageApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-covoiturageApp-params')});
                }
                return response;
            }
        };
    });
