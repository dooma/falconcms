(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('OrganizerDetailController', OrganizerDetailController);

    OrganizerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Organizer', 'User', 'Conference'];

    function OrganizerDetailController($scope, $rootScope, $stateParams, previousState, entity, Organizer, User, Conference) {
        var vm = this;

        vm.organizer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('falconcmsApp:organizerUpdate', function(event, result) {
            vm.organizer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
