(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('PaperDetailController', PaperDetailController);

    PaperDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Paper', 'Section'];

    function PaperDetailController($scope, $rootScope, $stateParams, previousState, entity, Paper, Section) {
        var vm = this;

        vm.paper = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('falconcmsApp:paperUpdate', function(event, result) {
            vm.paper = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
