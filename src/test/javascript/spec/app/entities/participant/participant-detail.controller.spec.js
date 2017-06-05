'use strict';

describe('Controller Tests', function() {

    describe('Participant Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockParticipant, MockSection, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockParticipant = jasmine.createSpy('MockParticipant');
            MockSection = jasmine.createSpy('MockSection');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Participant': MockParticipant,
                'Section': MockSection,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ParticipantDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'falconcmsApp:participantUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
