(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('PaperDeleteController',PaperDeleteController);

    PaperDeleteController.$inject = ['$uibModalInstance', 'entity', 'Paper'];

    function PaperDeleteController($uibModalInstance, entity, Paper) {
        var vm = this;

        vm.paper = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Paper.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
