(function() {
    'use strict';
    angular
        .module('falconcmsApp')
        .factory('Paper', Paper);

    Paper.$inject = ['$resource'];

    function Paper ($resource) {
        var resourceUrl =  'api/papers/:id';

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
