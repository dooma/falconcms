(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('ReviewDetailController', ReviewDetailController);

    ReviewDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Review', 'Organizer', 'Paper'];

    function ReviewDetailController($scope, $rootScope, $stateParams, previousState, entity, Review, Organizer, Paper) {
        var vm = this;

        vm.review = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('falconcmsApp:reviewUpdate', function(event, result) {
            vm.review = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
