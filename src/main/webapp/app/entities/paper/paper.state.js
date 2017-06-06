(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('paper', {
            parent: 'entity',
            url: '/paper',
            data: {
                authorities: ['ROLE_PARTICIPANT'],
                pageTitle: 'falconcmsApp.paper.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/paper/papers.html',
                    controller: 'PaperController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('paper');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('paper-detail', {
            parent: 'paper',
            url: '/paper/{id}',
            data: {
                authorities: ['ROLE_PARTICIPANT'],
                pageTitle: 'falconcmsApp.paper.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/paper/paper-detail.html',
                    controller: 'PaperDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('paper');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Paper', function($stateParams, Paper) {
                    return Paper.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'paper',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('paper-detail.edit', {
            parent: 'paper-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_PARTICIPANT']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/paper/paper-dialog.html',
                    controller: 'PaperDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Paper', function(Paper) {
                            return Paper.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('paper.new', {
            parent: 'paper',
            url: '/new',
            data: {
                authorities: ['ROLE_PARTICIPANT']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/paper/paper-dialog.html',
                    controller: 'PaperDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                keywords: null,
                                metaInfo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('paper', null, { reload: 'paper' });
                }, function() {
                    $state.go('paper');
                });
            }]
        })
        .state('paper.edit', {
            parent: 'paper',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_PARTICIPANT']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/paper/paper-dialog.html',
                    controller: 'PaperDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Paper', function(Paper) {
                            return Paper.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('paper', null, { reload: 'paper' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('paper.delete', {
            parent: 'paper',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_PARTICIPANT']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/paper/paper-delete-dialog.html',
                    controller: 'PaperDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Paper', function(Paper) {
                            return Paper.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('paper', null, { reload: 'paper' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
