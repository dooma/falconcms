(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('ConferenceController', ConferenceController);

    ConferenceController.$inject = ['Conference'];

    function ConferenceController(Conference) {

        var vm = this;

        vm.conferences = [];

        loadAll();

        function loadAll() {
            Conference.query(function(result) {
                vm.conferences = result;
                vm.searchQuery = null;
            });
        }
    }
})();
