(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('ParticipantDialogController', ParticipantDialogController);

    ParticipantDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Participant', 'Section', 'User'];

    function ParticipantDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Participant, Section, User) {
        var vm = this;

        vm.participant = entity;
        vm.clear = clear;
        vm.save = save;
        vm.sections = Section.query({filter: 'participant-is-null'});
        $q.all([vm.participant.$promise, vm.sections.$promise]).then(function() {
            if (!vm.participant.sectionId) {
                return $q.reject();
            }
            return Section.get({id : vm.participant.sectionId}).$promise;
        }).then(function(section) {
            vm.sections.push(section);
        });
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.participant.id !== null) {
                Participant.update(vm.participant, onSaveSuccess, onSaveError);
            } else {
                Participant.save(vm.participant, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('falconcmsApp:participantUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
