'use strict';

describe('Controller Tests', function() {

    describe('Driver Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDriver, MockPath;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDriver = jasmine.createSpy('MockDriver');
            MockPath = jasmine.createSpy('MockPath');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Driver': MockDriver,
                'Path': MockPath
            };
            createController = function() {
                $injector.get('$controller')("DriverDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'covoiturageApp:driverUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
