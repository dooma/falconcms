(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('PaperAuthorDetailController', PaperAuthorDetailController);

    PaperAuthorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PaperAuthor', 'Paper', 'Author'];

    function PaperAuthorDetailController($scope, $rootScope, $stateParams, previousState, entity, PaperAuthor, Paper, Author) {
        var vm = this;

        vm.paperAuthor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('falconcmsApp:paperAuthorUpdate', function(event, result) {
            vm.paperAuthor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
