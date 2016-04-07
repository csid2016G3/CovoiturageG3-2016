'use strict';

angular.module('covoiturageApp').controller('DriverDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Driver', 'Path',
        function($scope, $stateParams, $uibModalInstance, entity, Driver, Path) {

        $scope.driver = entity;
        $scope.paths = Path.query();
        $scope.load = function(id) {
            Driver.get({id : id}, function(result) {
                $scope.driver = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('covoiturageApp:driverUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.driver.id != null) {
                Driver.update($scope.driver, onSaveSuccess, onSaveError);
            } else {
                Driver.save($scope.driver, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
