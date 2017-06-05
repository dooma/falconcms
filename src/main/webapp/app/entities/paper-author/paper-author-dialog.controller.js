(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('PaperAuthorDialogController', PaperAuthorDialogController);

    PaperAuthorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PaperAuthor', 'Paper', 'Author'];

    function PaperAuthorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PaperAuthor, Paper, Author) {
        var vm = this;

        vm.paperAuthor = entity;
        vm.clear = clear;
        vm.save = save;
        vm.papers = Paper.query();
        vm.authors = Author.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.paperAuthor.id !== null) {
                PaperAuthor.update(vm.paperAuthor, onSaveSuccess, onSaveError);
            } else {
                PaperAuthor.save(vm.paperAuthor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('falconcmsApp:paperAuthorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
