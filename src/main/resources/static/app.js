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

    // maquina services
    monitor.service('MaquinaService', function ($http) {
    	this.getAll = function () {
            return $http.get('/maquinas');
        }
    	this.get = function (codigo) {
            return $http.get('/maquinas/' + codigo);
        }
        this.getLog = function (codigo) {
            return $http.get('/maquinas/' + codigo);
        }
    });
    
    monitor.controller('MaquinaCtrl', ['MaquinaService', '$scope','$routeParams', function (MaquinaService, $scope, $routeParams) {
        var self = this;
        self.data = {};

        function refresh() {
            MaquinaService.get($routeParams.codigo).then(function (response) {
                self.data = response.data;
                self.data.log = [];
                MaquinaService.get($routeParams.codigo).then(function (response) {
                    self.data = response.data;
                    self.data.log = [];
                });
            });
        }

        refresh();

        function initView() {

            var sock = new SockJS('/api/notify');
            sock.onmessage = function (response) {
                var msg = JSON.parse(response.data);

                if (self.data.codigo == msg.data.maquina) {
                    self.data.tempo = msg.data.tempoFormatado;
                    self.data.situacao = msg.data.modo;
                    self.data.operador = msg.data.operador;

                    self.data.log.reverse().push(msg);

                    if (self.data.log.length > 10) {
                        self.data.log = self.data.log.slice(self.data.log.length - 10);
                    }

                    self.data.log = self.data.log.reverse();

                    $scope.$apply();
                }

            };
        }

        initView();          
    }]);
    
    // menu services
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

                    var segundos = parseInt(self.options[i].tempo, 10); 
                    var segundo = parseInt(segundos % 60, 10); 
                    var minutos = parseInt(segundos / 60, 10); 
                    var minuto = parseInt(minutos % 60, 10); 
                    var horas = parseInt(minutos / 60, 10);
                    var hora = parseInt(horas % 60, 10);
                    var dias = parseInt(horas / 24, 10); 
                    var dia = parseInt(dias % 24, 10);
                    
                    if (dia > 1) {
                        self.options[i].tempo = dia + ' dias'
                    } else if (dia == 1) {
                        self.options[i].tempo = dia + ' dia'
                    } else if (hora > 0) {
                        self.options[i].tempo = hora + ' h'
                    } else if (minuto > 0) {
                        self.options[i].tempo = minuto + ' min'
                    } else {
                        self.options[i].tempo = segundo + ' s';
                    }
                }
            });
        }

        refresh();
        
        function initView() {

            var sock = new SockJS('/api/notify');
            sock.onmessage = function (response) {
                var msg = JSON.parse(response.data);

                for( var i = 0; i < self.options.length; ++i ) {
                	if (self.options[i].codigo == msg.data.maquina) {
                		self.options[i].situacao = msg.data.modo;

                        var segundos = parseInt(msg.data.tempo, 10); 
                        var segundo = parseInt(segundos % 60, 10); 
                        var minutos = parseInt(segundos / 60, 10); 
                        var minuto = parseInt(minutos % 60, 10); 
                        var horas = parseInt(minutos / 60, 10);
                        var hora = parseInt(horas % 60, 10);
                        var dias = parseInt(horas / 24, 10); 
                        var dia = parseInt(dias % 24, 10);
                        
                        if (dia > 1) {
                            self.options[i].tempo = dia + ' dias'
                        } else if (dia == 1) {
                            self.options[i].tempo = dia + ' dia'
                        } else if (hora > 0) {
                            self.options[i].tempo = hora + ' h'
                        } else if (minuto > 0) {
                            self.options[i].tempo = minuto + ' min'
                        } else {
                            self.options[i].tempo = segundo + ' s';
                        }
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
            case 0: return 'danger';
            case 1: return 'success';
            case 2: return 'info';
            case 3: return 'info';
            case 4: return 'info';
            case 5: return 'danger';
            case 6: return 'success';
            case 7: return 'warning';
            case 99: return 'warning';
            }
        }
    });

    monitor.filter('statusPanelClass', function () {
        return function (input) {
            switch (input) {
            case 0: return 'panel-danger';
            case 1: return 'panel-success';
            case 2: return 'panel-info';
            case 3: return 'panel-info';
            case 4: return 'panel-info';
            case 5: return 'panel-danger';
            case 6: return 'panel-success';
            case 7: return 'panel-warning';
            case 99: return 'panel-warning';
            }
        }
    });   

    monitor.filter('statusName', function () {
        return function (input) {
            switch (input) {
            case 0: return 'Parada Indeterminado';
            case 1: return 'Ociosa';
            case 2: return 'Produzindo';
            case 3: return 'Manual';
            case 4: return 'Preparacao';
            case 5: return 'Manutencao';
            case 6: return 'Maquina Desligada';
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

    monitor.controller('DashboardCtrl', ['MaquinaService', '$scope','$routeParams', function (MaquinaService, $scope, $routeParams) {
        var self = this;
        self.maquinas = [];
        self.count = 0;

        function refresh() {
            MaquinaService.getAll().then(function (response) {
                self.maquinas = response.data._embedded.maquinas;

                for( var i = 0; i < self.maquinas.length; ++i ) {

                    var segundos = parseInt(self.maquinas[i].tempo, 10); 
                    var segundo = parseInt(segundos % 60, 10); 
                    var minutos = parseInt(segundos / 60, 10); 
                    var minuto = parseInt(minutos % 60, 10); 
                    var horas = parseInt(minutos / 60, 10);
                    var hora = parseInt(horas % 60, 10);
                    var dias = parseInt(horas / 24, 10); 
                    var dia = parseInt(dias % 24, 10);
                    
                    if (dia > 1) {
                        self.maquinas[i].tempo = dia + ' dias'
                    } else if (dia == 1) {
                        self.maquinas[i].tempo = dia + ' dia'
                    } else if (hora > 0) {
                        self.maquinas[i].tempo = hora + ' h'
                    } else if (minuto > 0) {
                        self.maquinas[i].tempo = minuto + ' min'
                    } else {
                        self.maquinas[i].tempo = segundo + ' s';
                    }
                }
            });
        }

        refresh();
        
        function initView() {

            var sock = new SockJS('http://127.0.0.1:8080/api/notify');
            sock.onmessage = function (response) {
                var msg = JSON.parse(response.data);

                for( var i = 0; i < self.maquinas.length; ++i ) {
                    if (self.maquinas[i].codigo == msg.data.maquina) {
                        //self.maquinas[i].tempo = msg.data.tempoFormatado;
                        self.maquinas[i].situacao = msg.data.modo;
                        self.maquinas[i].operador = msg.data.operador;

                        var segundos = parseInt(msg.data.tempo, 10); 
                        var segundo = parseInt(segundos % 60, 10); 
                        var minutos = parseInt(segundos / 60, 10); 
                        var minuto = parseInt(minutos % 60, 10); 
                        var horas = parseInt(minutos / 60, 10);
                        var hora = parseInt(horas % 60, 10);
                        var dias = parseInt(horas / 24, 10); 
                        var dia = parseInt(dias % 24, 10);
                        
                        if (dia > 1) {
                            self.maquinas[i].tempo = dia + ' dias'
                        } else if (dia == 1) {
                            self.maquinas[i].tempo = dia + ' dia'
                        } else if (hora > 0) {
                            self.maquinas[i].tempo = hora + ' h'
                        } else if (minuto > 0) {
                            self.maquinas[i].tempo = minuto + ' min'
                        } else {
                            self.maquinas[i].tempo = segundo + ' s';
                        }
                    }
                }
                
                $scope.$apply();
            };
        };

        initView(); 
    }]);
    
    monitor.config(function ($routeProvider) {
        $routeProvider.when('/home', {templateUrl: '/pages/home.tpl.html'});
        $routeProvider.when('/index', {templateUrl: '/template/index.html'});
        $routeProvider.when('/sqs', {templateUrl: '/pages/sqs.tpl.html'});
        $routeProvider.when('/sns', {templateUrl: '/pages/sns.tpl.html'});
        $routeProvider.when('/elasticache', {templateUrl: '/pages/elasticache.tpl.html'});
        $routeProvider.when('/rds', {templateUrl: '/pages/rds.tpl.html'});
        $routeProvider.when('/ec2', {templateUrl: '/pages/ec2.tpl.html'});
        $routeProvider.when('/main', {templateUrl: '/pages/main.tpl.html'});
        $routeProvider.when('/dashboard', {templateUrl: '/pages/dashboard.html'});
        $routeProvider.when('/maquina/:codigo', {templateUrl: '/pages/maquina.html'});
        $routeProvider.otherwise({redirectTo: '/dashboard'});
        //$routeProvider.otherwise({redirectTo: '/main'});
    });
}());