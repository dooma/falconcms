'use strict';

describe('Controller Tests', function() {

    describe('PaperAuthor Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPaperAuthor, MockPaper, MockAuthor;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPaperAuthor = jasmine.createSpy('MockPaperAuthor');
            MockPaper = jasmine.createSpy('MockPaper');
            MockAuthor = jasmine.createSpy('MockAuthor');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PaperAuthor': MockPaperAuthor,
                'Paper': MockPaper,
                'Author': MockAuthor
            };
            createController = function() {
                $injector.get('$controller')("PaperAuthorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'falconcmsApp:paperAuthorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
