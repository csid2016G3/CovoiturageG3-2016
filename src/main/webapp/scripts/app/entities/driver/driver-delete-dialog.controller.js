'use strict';

angular.module('covoiturageApp')
	.controller('DriverDeleteController', function($scope, $uibModalInstance, entity, Driver) {

        $scope.driver = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Driver.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
