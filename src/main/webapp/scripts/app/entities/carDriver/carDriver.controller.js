'use strict';

angular.module('covoiturageApp')
    .controller('CarDriverController', function ($scope, $state, CarDriver) {

        $scope.carDrivers = [];
        $scope.loadAll = function() {
            CarDriver.query(function(result) {
               $scope.carDrivers = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.carDriver = {
                firstName: null,
                lastName: null,
                exp: null,
                id: null
            };
        };
    });
