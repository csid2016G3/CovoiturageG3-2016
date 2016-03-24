'use strict';

describe('Controller Tests', function() {

    describe('CarDriver Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCarDriver;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCarDriver = jasmine.createSpy('MockCarDriver');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'CarDriver': MockCarDriver
            };
            createController = function() {
                $injector.get('$controller')("CarDriverDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'covoiturageApp:carDriverUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
