(function() {
    'use strict';
    angular
        .module('falconcmsApp')
        .factory('Participant', Participant);

    Participant.$inject = ['$resource'];

    function Participant ($resource) {
        var resourceUrl =  'api/participants/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
