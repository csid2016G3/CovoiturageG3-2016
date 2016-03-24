'use strict';

angular.module('covoiturageApp')
	.controller('CarDriverDeleteController', function($scope, $uibModalInstance, entity, CarDriver) {

        $scope.carDriver = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CarDriver.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
