(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('PaperAuthorController', PaperAuthorController);

    PaperAuthorController.$inject = ['PaperAuthor'];

    function PaperAuthorController(PaperAuthor) {

        var vm = this;

        vm.paperAuthors = [];

        loadAll();

        function loadAll() {
            PaperAuthor.query(function(result) {
                vm.paperAuthors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
