(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('ConferenceDialogController', ConferenceDialogController);

    ConferenceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Conference'];

    function ConferenceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Conference) {
        var vm = this;

        vm.conference = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.conference.id !== null) {
                Conference.update(vm.conference, onSaveSuccess, onSaveError);
            } else {
                Conference.save(vm.conference, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('falconcmsApp:conferenceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateStart = false;
        vm.datePickerOpenStatus.dateStop = false;
        vm.datePickerOpenStatus.callForPapers = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
