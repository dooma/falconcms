(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('OrganizerDialogController', OrganizerDialogController);

    OrganizerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Organizer', 'User', 'Conference'];

    function OrganizerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Organizer, User, Conference) {
        var vm = this;

        vm.organizer = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.conferences = Conference.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.organizer.id !== null) {
                Organizer.update(vm.organizer, onSaveSuccess, onSaveError);
            } else {
                Organizer.save(vm.organizer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('falconcmsApp:organizerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
