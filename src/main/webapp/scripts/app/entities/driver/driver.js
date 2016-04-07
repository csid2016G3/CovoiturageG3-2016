'use strict';

angular.module('covoiturageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('driver', {
                parent: 'entity',
                url: '/drivers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'covoiturageApp.driver.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/driver/drivers.html',
                        controller: 'DriverController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('driver');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('driver.detail', {
                parent: 'entity',
                url: '/driver/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'covoiturageApp.driver.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/driver/driver-detail.html',
                        controller: 'DriverDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('driver');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Driver', function($stateParams, Driver) {
                        return Driver.get({id : $stateParams.id});
                    }]
                }
            })
            .state('driver.new', {
                parent: 'driver',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/driver/driver-dialog.html',
                        controller: 'DriverDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
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
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('driver', null, { reload: true });
                    }, function() {
                        $state.go('driver');
                    })
                }]
            })
            .state('driver.edit', {
                parent: 'driver',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/driver/driver-dialog.html',
                        controller: 'DriverDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Driver', function(Driver) {
                                return Driver.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('driver', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('driver.delete', {
                parent: 'driver',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/driver/driver-delete-dialog.html',
                        controller: 'DriverDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Driver', function(Driver) {
                                return Driver.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('driver', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
