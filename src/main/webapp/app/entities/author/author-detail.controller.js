(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('AuthorDetailController', AuthorDetailController);

    AuthorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Author', 'User'];

    function AuthorDetailController($scope, $rootScope, $stateParams, previousState, entity, Author, User) {
        var vm = this;

        vm.author = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('falconcmsApp:authorUpdate', function(event, result) {
            vm.author = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
