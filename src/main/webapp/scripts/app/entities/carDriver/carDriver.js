'use strict';

angular.module('covoiturageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('carDriver', {
                parent: 'entity',
                url: '/carDrivers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'covoiturageApp.carDriver.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/carDriver/carDrivers.html',
                        controller: 'CarDriverController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('carDriver');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('carDriver.detail', {
                parent: 'entity',
                url: '/carDriver/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'covoiturageApp.carDriver.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/carDriver/carDriver-detail.html',
                        controller: 'CarDriverDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('carDriver');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CarDriver', function($stateParams, CarDriver) {
                        return CarDriver.get({id : $stateParams.id});
                    }]
                }
            })
            .state('carDriver.new', {
                parent: 'carDriver',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/carDriver/carDriver-dialog.html',
                        controller: 'CarDriverDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    firstName: null,
                                    lastName: null,
                                    exp: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('carDriver', null, { reload: true });
                    }, function() {
                        $state.go('carDriver');
                    })
                }]
            })
            .state('carDriver.edit', {
                parent: 'carDriver',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/carDriver/carDriver-dialog.html',
                        controller: 'CarDriverDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CarDriver', function(CarDriver) {
                                return CarDriver.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('carDriver', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('carDriver.delete', {
                parent: 'carDriver',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/carDriver/carDriver-delete-dialog.html',
                        controller: 'CarDriverDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CarDriver', function(CarDriver) {
                                return CarDriver.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('carDriver', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
