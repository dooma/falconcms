(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('ConferenceDetailController', ConferenceDetailController);

    ConferenceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Conference'];

    function ConferenceDetailController($scope, $rootScope, $stateParams, previousState, entity, Conference) {
        var vm = this;

        vm.conference = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('falconcmsApp:conferenceUpdate', function(event, result) {
            vm.conference = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
