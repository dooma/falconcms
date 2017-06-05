(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('PaperDialogController', PaperDialogController);

    PaperDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Paper', 'Section'];

    function PaperDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Paper, Section) {
        var vm = this;

        vm.paper = entity;
        vm.clear = clear;
        vm.save = save;
        vm.sections = Section.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.paper.id !== null) {
                Paper.update(vm.paper, onSaveSuccess, onSaveError);
            } else {
                Paper.save(vm.paper, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('falconcmsApp:paperUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
