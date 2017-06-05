(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('paper-author', {
            parent: 'entity',
            url: '/paper-author',
            data: {
                authorities: ['ROLE_PARTICIPANT'],
                pageTitle: 'falconcmsApp.paperAuthor.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/paper-author/paper-authors.html',
                    controller: 'PaperAuthorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('paperAuthor');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('paper-author-detail', {
            parent: 'paper-author',
            url: '/paper-author/{id}',
            data: {
                authorities: ['ROLE_PARTICIPANT'],
                pageTitle: 'falconcmsApp.paperAuthor.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/paper-author/paper-author-detail.html',
                    controller: 'PaperAuthorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('paperAuthor');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PaperAuthor', function($stateParams, PaperAuthor) {
                    return PaperAuthor.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'paper-author',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('paper-author-detail.edit', {
            parent: 'paper-author-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_PARTICIPANT']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/paper-author/paper-author-dialog.html',
                    controller: 'PaperAuthorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PaperAuthor', function(PaperAuthor) {
                            return PaperAuthor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('paper-author.new', {
            parent: 'paper-author',
            url: '/new',
            data: {
                authorities: ['ROLE_PARTICIPANT']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/paper-author/paper-author-dialog.html',
                    controller: 'PaperAuthorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('paper-author', null, { reload: 'paper-author' });
                }, function() {
                    $state.go('paper-author');
                });
            }]
        })
        .state('paper-author.edit', {
            parent: 'paper-author',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_PARTICIPANT']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/paper-author/paper-author-dialog.html',
                    controller: 'PaperAuthorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PaperAuthor', function(PaperAuthor) {
                            return PaperAuthor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('paper-author', null, { reload: 'paper-author' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('paper-author.delete', {
            parent: 'paper-author',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_PARTICIPANT']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/paper-author/paper-author-delete-dialog.html',
                    controller: 'PaperAuthorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PaperAuthor', function(PaperAuthor) {
                            return PaperAuthor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('paper-author', null, { reload: 'paper-author' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
