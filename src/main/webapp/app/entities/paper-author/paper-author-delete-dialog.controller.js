(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('PaperAuthorDeleteController',PaperAuthorDeleteController);

    PaperAuthorDeleteController.$inject = ['$uibModalInstance', 'entity', 'PaperAuthor'];

    function PaperAuthorDeleteController($uibModalInstance, entity, PaperAuthor) {
        var vm = this;

        vm.paperAuthor = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PaperAuthor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
