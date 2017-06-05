(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('organizer', {
            parent: 'entity',
            url: '/organizer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'falconcmsApp.organizer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/organizer/organizers.html',
                    controller: 'OrganizerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('organizer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('organizer-detail', {
            parent: 'organizer',
            url: '/organizer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'falconcmsApp.organizer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/organizer/organizer-detail.html',
                    controller: 'OrganizerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('organizer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Organizer', function($stateParams, Organizer) {
                    return Organizer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'organizer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('organizer-detail.edit', {
            parent: 'organizer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/organizer/organizer-dialog.html',
                    controller: 'OrganizerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Organizer', function(Organizer) {
                            return Organizer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('organizer.new', {
            parent: 'organizer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/organizer/organizer-dialog.html',
                    controller: 'OrganizerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                affiliation: null,
                                email: null,
                                web: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('organizer', null, { reload: 'organizer' });
                }, function() {
                    $state.go('organizer');
                });
            }]
        })
        .state('organizer.edit', {
            parent: 'organizer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/organizer/organizer-dialog.html',
                    controller: 'OrganizerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Organizer', function(Organizer) {
                            return Organizer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('organizer', null, { reload: 'organizer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('organizer.delete', {
            parent: 'organizer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/organizer/organizer-delete-dialog.html',
                    controller: 'OrganizerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Organizer', function(Organizer) {
                            return Organizer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('organizer', null, { reload: 'organizer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
