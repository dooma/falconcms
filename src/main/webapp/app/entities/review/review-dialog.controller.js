(function() {
    'use strict';

    angular
        .module('falconcmsApp')
        .controller('ReviewDialogController', ReviewDialogController);

    ReviewDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Review', 'Organizer', 'Paper'];

    function ReviewDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Review, Organizer, Paper) {
        var vm = this;

        vm.review = entity;
        vm.clear = clear;
        vm.save = save;
        vm.reviewers = Organizer.query({filter: 'review-is-null'});
        $q.all([vm.review.$promise, vm.reviewers.$promise]).then(function() {
            if (!vm.review.reviewerId) {
                return $q.reject();
            }
            return Organizer.get({id : vm.review.reviewerId}).$promise;
        }).then(function(reviewer) {
            vm.reviewers.push(reviewer);
        });
        vm.papers = Paper.query({filter: 'review-is-null'});
        $q.all([vm.review.$promise, vm.papers.$promise]).then(function() {
            if (!vm.review.paperId) {
                return $q.reject();
            }
            return Paper.get({id : vm.review.paperId}).$promise;
        }).then(function(paper) {
            vm.papers.push(paper);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.review.id !== null) {
                Review.update(vm.review, onSaveSuccess, onSaveError);
            } else {
                Review.save(vm.review, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('falconcmsApp:reviewUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
