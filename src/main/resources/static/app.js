/* global angular,hljs */
(function () {
    var monitor = angular.module('monitor', ['ngRoute']);

    // Global stuff
    monitor.directive('active', function ($location) {
        return {
            link: function (scope, element) {
                function makeActiveIfMatchesCurrentPath() {
                    if ($location.path().indexOf(element.find('a').attr('href').substr(1)) > -1) {
                        element.addClass('active');
                    } else {
                        element.removeClass('active');
                    }
                }

                scope.$on('$routeChangeSuccess', function () {
                    makeActiveIfMatchesCurrentPath();
                });
            }
        };
    });

    monitor.service('SourceCodeService', function ($http) {
        this.getSourceCode = function (path) {
            return $http.get('source?path=' + path);
        };
    });

    monitor.directive('sourceCode', function (SourceCodeService, $timeout) {
        return {
            restrict: 'E',
            templateUrl: 'templates/source-box.tpl.html',
            scope: {
                path: '@'
            },
            link: function (scope, element) {
                SourceCodeService.getSourceCode(scope.path).then(function (response) {
                    scope.code = response.data.content;
                    scope.link = response.data.url;
                    scope.title = response.data.name;
                    $timeout(function () {
                        hljs.highlightBlock(element.find('code')[0]);
                    });
                });
            }
        }
    });

    // menu services
    monitor.service('MaquinaService', function ($http) {
    	this.getAll = function () {
            return $http.get('/maquinas');
        }
    });

    monitor.controller('MenuCtrl', function (MaquinaService, $scope) {
    	var self = this;
        self.options = [];
        self.count = 0;

        function refresh() {
        	MaquinaService.getAll().then(function (response) {
                self.options = response.data._embedded.maquinas;
                for( var i = 0; i < self.options.length; ++i ) {
                	if (self.options[i].situacao == '0' ||
                		self.options[i].situacao == '5' ||
                		self.options[i].situacao == '99') {
                		self.count++;
                	}
                }
            });
        }

        refresh();
        
        function initView() {

            var sock = new SockJS('/notify');
            sock.onmessage = function (response) {
                var msg = JSON.parse(response.data);

                for( var i = 0; i < self.options.length; ++i ) {
                	if (self.options[i].codigo == msg.data.maquina) {
                		self.options[i].tempo = msg.data.tempo;
                		self.options[i].situacao = msg.data.modo;
                	}
                }
                
                self.count = 0;
                for( var i = 0; i < self.options.length; ++i ) {
                	if (self.options[i].situacao == '0' ||
                		self.options[i].situacao == '5' ||
                		self.options[i].situacao == '99') {
                		self.count++;
                	}
                }
                
                $scope.$apply();
            };
        }

        initView();        

    });
    
    // end menu services
    
    // SQS
    monitor.service('SqsService', function ($http) {
        this.sendMessage = function (message) {
            return $http.post('sqs/message-processing-queue', message);
        };
    });

    monitor.controller('SqsCtrl', function (SqsService, $scope) {
        var self = this;
        self.model = {};
        self.responses = [];

        function initMessageToProcess() {
            self.model.messageToProcess = {
                message: undefined,
                priority: 2
            };
        }

        self.sendMessage = function () {
            SqsService.sendMessage(self.model.messageToProcess);
            initMessageToProcess();
        };

        function initView() {
            initMessageToProcess();

            var sock = new SockJS('/notify');
            sock.onmessage = function (e) {
                var jsonResponse = JSON.parse(e.data);
                self.responses.reverse().push(jsonResponse);

                if (self.responses.length > 10) {
                    self.responses = self.responses.slice(self.responses.length - 10);
                }

                self.responses = self.responses.reverse();
                $scope.$apply();
            };
        }

        initView();
    });

    monitor.filter('statusClass', function () {
        return function (input) {
            switch (input) {
            case 0: return 'badge badge-danger';
            case 1: return 'label label-info';
            case 2: return 'label label-success';
            case 3: return 'label label-info';
            case 4: return 'label label-info';
            case 5: return 'badge badge-danger';
            case 6: return 'label label-info';
            case 7: return 'label label-warning';
            case 99: return 'badge badge-danger';
            }
        }
    });
    
    monitor.filter('modoOperacao', function () {
        return function (input) {
            switch (input) {
            case 0: return 'Parada Indeterminado';
            case 1: return 'Ociosa';
            case 2: return 'Produzindo';
            case 3: return 'Manual';
            case 4: return 'Preparacao';
            case 5: return 'Manutencao';
            case 6: return 'Maq. Desligada';
            case 7: return 'IHM Desligada';
            case 99: return 'Falha Comunicacao';
            }
        }
    });    
    
    monitor.filter('priority', function () {
        return function (input) {
            switch (input) {
                case 1:
                    return 'Low';
                case 2:
                    return 'Medium';
                case 3:
                    return 'High';
            }
        }
    });

    // SNS
    monitor.service('SnsService', function ($http) {
        this.send = function (message) {
            return $http.post('sns/send', message);
        };
    });

    monitor.controller('SnsCtrl', function (SnsService, $scope) {
        var self = this;
        self.responses = [];

        function initModel() {
            self.model = {
                message: undefined,
                subject: undefined
            };
        }

        self.send = function () {
            SnsService.send(self.model);
            initModel();
        };

        function initView() {
            initModel();

            var sock = new SockJS('/sns-messages');
            sock.onmessage = function (e) {
                var jsonResponse = JSON.parse(e.data);
                self.responses.reverse().push(jsonResponse);

                if (self.responses.length > 10) {
                    self.responses = self.responses.slice(self.responses.length - 10);
                }

                self.responses = self.responses.reverse();
                $scope.$apply();
            };
        }

        initView();
    });

    // RDS
    monitor.service('PersonService', function ($http) {
        this.add = function (person) {
            return $http.post('persons', person);
        };

        this.getAll = function () {
            return $http.get('persons');
        }
    });

    monitor.controller('RdsCtrl', function (PersonService) {
        var self = this;
        self.persons = [];

        function refresh() {
            PersonService.getAll().then(function (response) {
                self.persons = response.data;
            });
        }

        refresh();

        function initView() {
            self.model = {
                firstName: undefined,
                lastName: undefined
            };
        }

        initView();

        self.add = function () {
            PersonService.add(self.model).then(function () {
                refresh();
            });
            initView();
        };
    });

    // ElastiCache
    monitor.service('ElastiCacheService', function ($http) {
        this.getValue = function (key) {
            return $http.get('cachedService?key=' + key, {headers: {Accept: 'text/plain'}});
        };
    });

    monitor.controller('ElastiCacheCtrl', function ($scope, ElastiCacheService) {
        var self = this;
        self.loading = false;

        self.getValue = function () {
            self.value = '';
            self.loading = true;
            ElastiCacheService.getValue(self.key).then(function (response) {
                self.loading = false;
                self.value = response.data;
            });
        };
    });

    // EC2
    monitor.service('Ec2Service', function ($http) {
        this.getProperties = function () {
            return $http.get('info');
        };
    });

    monitor.controller('Ec2Ctrl', function (Ec2Service) {
        var self = this;

        Ec2Service.getProperties().then(function (response) {
            self.properties = response.data;
        });
    });

    monitor.config(function ($routeProvider) {
        $routeProvider.when('/home', {templateUrl: 'pages/home.tpl.html'});
        $routeProvider.when('/sqs', {templateUrl: 'pages/sqs.tpl.html'});
        $routeProvider.when('/sns', {templateUrl: 'pages/sns.tpl.html'});
        $routeProvider.when('/elasticache', {templateUrl: 'pages/elasticache.tpl.html'});
        $routeProvider.when('/rds', {templateUrl: 'pages/rds.tpl.html'});
        $routeProvider.when('/ec2', {templateUrl: 'pages/ec2.tpl.html'});
        $routeProvider.when('/main', {templateUrl: 'pages/main.tpl.html'});
        $routeProvider.otherwise({redirectTo: '/home'});
        //$routeProvider.otherwise({redirectTo: '/main'});
    });
}());