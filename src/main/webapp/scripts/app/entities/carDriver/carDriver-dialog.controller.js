'use strict';

angular.module('covoiturageApp').controller('CarDriverDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'CarDriver',
        function($scope, $stateParams, $uibModalInstance, entity, CarDriver) {

        $scope.carDriver = entity;
        $scope.load = function(id) {
            CarDriver.get({id : id}, function(result) {
                $scope.carDriver = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('covoiturageApp:carDriverUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.carDriver.id != null) {
                CarDriver.update($scope.carDriver, onSaveSuccess, onSaveError);
            } else {
                CarDriver.save($scope.carDriver, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
