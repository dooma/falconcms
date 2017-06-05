(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('SectionDetailController', SectionDetailController);

    SectionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Section', 'Conference'];

    function SectionDetailController($scope, $rootScope, $stateParams, previousState, entity, Section, Conference) {
        var vm = this;

        vm.section = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('falconcmsApp:sectionUpdate', function(event, result) {
            vm.section = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
