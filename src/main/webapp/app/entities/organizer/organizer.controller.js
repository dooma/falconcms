(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('OrganizerController', OrganizerController);

    OrganizerController.$inject = ['Organizer'];

    function OrganizerController(Organizer) {

        var vm = this;

        vm.organizers = [];

        loadAll();

        function loadAll() {
            Organizer.query(function(result) {
                vm.organizers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
