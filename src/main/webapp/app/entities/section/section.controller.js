(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('SectionController', SectionController);

    SectionController.$inject = ['Section'];

    function SectionController(Section) {

        var vm = this;

        vm.sections = [];

        loadAll();

        function loadAll() {
            Section.query(function(result) {
                vm.sections = result;
                vm.searchQuery = null;
            });
        }
    }
})();
