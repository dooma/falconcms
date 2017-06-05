(function() {
    'use strict';
    angular
        .module('falconcmsApp')
        .factory('Conference', Conference);

    Conference.$inject = ['$resource', 'DateUtils'];

    function Conference ($resource, DateUtils) {
        var resourceUrl =  'api/conferences/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateStart = DateUtils.convertDateTimeFromServer(data.dateStart);
                        data.dateStop = DateUtils.convertDateTimeFromServer(data.dateStop);
                        data.callForPapers = DateUtils.convertDateTimeFromServer(data.callForPapers);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
