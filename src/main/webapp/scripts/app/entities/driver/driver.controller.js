'use strict';

angular.module('covoiturageApp')
    .controller('DriverController', function ($scope, $state, Driver) {

        $scope.drivers = [];
        $scope.loadAll = function() {
            Driver.query(function(result) {
               $scope.drivers = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.driver = {
                driverFirstName: null,
                driverLastName: null,
                driverPhone: null,
                driverAddress: null,
                driverMail: null,
                driverAge: null,
                driverExperience: null,
                driverExp: null,
                id: null
            };
        };
    });
