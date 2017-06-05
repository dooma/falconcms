(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('ReviewController', ReviewController);

    ReviewController.$inject = ['Review'];

    function ReviewController(Review) {

        var vm = this;

        vm.reviews = [];

        loadAll();

        function loadAll() {
            Review.query(function(result) {
                vm.reviews = result;
                vm.searchQuery = null;
            });
        }
    }
})();
