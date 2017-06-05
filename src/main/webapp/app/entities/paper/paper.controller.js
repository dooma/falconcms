(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('PaperController', PaperController);

    PaperController.$inject = ['Paper'];

    function PaperController(Paper) {

        var vm = this;

        vm.papers = [];

        loadAll();

        function loadAll() {
            Paper.query(function(result) {
                vm.papers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
