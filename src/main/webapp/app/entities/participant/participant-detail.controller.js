(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('ParticipantDetailController', ParticipantDetailController);

    ParticipantDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Participant', 'Section', 'User'];

    function ParticipantDetailController($scope, $rootScope, $stateParams, previousState, entity, Participant, Section, User) {
        var vm = this;

        vm.participant = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('falconcmsApp:participantUpdate', function(event, result) {
            vm.participant = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
