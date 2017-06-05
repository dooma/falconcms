(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('conference', {
            parent: 'entity',
            url: '/conference',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'falconcmsApp.conference.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/conference/conferences.html',
                    controller: 'ConferenceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('conference');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('conference-detail', {
            parent: 'conference',
            url: '/conference/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'falconcmsApp.conference.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/conference/conference-detail.html',
                    controller: 'ConferenceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('conference');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Conference', function($stateParams, Conference) {
                    return Conference.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'conference',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('conference-detail.edit', {
            parent: 'conference-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conference/conference-dialog.html',
                    controller: 'ConferenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Conference', function(Conference) {
                            return Conference.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('conference.new', {
            parent: 'conference',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conference/conference-dialog.html',
                    controller: 'ConferenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                dateStart: null,
                                dateStop: null,
                                callForPapers: null,
                                location: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('conference', null, { reload: 'conference' });
                }, function() {
                    $state.go('conference');
                });
            }]
        })
        .state('conference.edit', {
            parent: 'conference',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conference/conference-dialog.html',
                    controller: 'ConferenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Conference', function(Conference) {
                            return Conference.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('conference', null, { reload: 'conference' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('conference.delete', {
            parent: 'conference',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conference/conference-delete-dialog.html',
                    controller: 'ConferenceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Conference', function(Conference) {
                            return Conference.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('conference', null, { reload: 'conference' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
