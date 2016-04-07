'use strict';

angular.module('covoiturageApp')
    .controller('DriverDetailController', function ($scope, $rootScope, $stateParams, entity, Driver, Path) {
        $scope.driver = entity;
        $scope.load = function (id) {
            Driver.get({id: id}, function(result) {
                $scope.driver = result;
            });
        };
        var unsubscribe = $rootScope.$on('covoiturageApp:driverUpdate', function(event, result) {
            $scope.driver = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
