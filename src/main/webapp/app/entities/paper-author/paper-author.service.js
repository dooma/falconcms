(function() {
    'use strict';
    angular
        .module('falconcmsApp')
        .factory('PaperAuthor', PaperAuthor);

    PaperAuthor.$inject = ['$resource'];

    function PaperAuthor ($resource) {
        var resourceUrl =  'api/paper-authors/:id';

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
