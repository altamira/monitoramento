/* global angular,hljs */
(function () {
    var module = angular.module('monitor', ['ngRoute']);
                     
    module.directive('areachart', function ($window) {

        return {
            restrict: 'E',
            template: '<div></div>',
            replace: true,
            link: function ($scope, element, attrs) {
                var morris;
                angular.element($window).bind('resize', function () {
                    if (morris) {
                        //console.log('morris resized');
                        morris.redraw();
                    }
                });

                attrs.$observe('value', function (val) {
                    if (!morris) {
                        console.log('creating chart');
                        morris = Morris.Bar({
                            element: element,
                            data: angular.fromJson(val),
                            xkey: $scope[attrs.xkey],
                            ykeys: $scope[attrs.ykeys],
                            labels: $scope[attrs.labels],
                            lineColors: ['#fff'],
                            lineWidth: 2,
                            pointSize: 4,
                            gridLineColor: 'rgba(255,255,255,.5)',
                            resize: true,
                            gridTextColor: '#fff',
                            barColors: function (row, series, type) {
                                //console.log("--> "+row.label, series, type);
                                if(row.x == "0") return "#AD1D28";
                                else if(row.x == "1") return "#DEBB27";
                                else if(row.x == "2") return "#fec04c";
                                else if(row.x == "3") return "#1AB244";
                                else if(row.x == "4") return "#1AB444";
                                else if(row.x == "5") return "#1ABf44";
                                else if(row.x == "6") return "#3AB244";
                                else if(row.x == "7") return "#1cB244";
                                else if(row.x == "99") return "#4AB144";
                            }
                        });
                    } else {
                        //console.log('setting chart values');
                        morris.setData(angular.fromJson(val));
                    }
                });
            }
        };
    });

    module.directive('barchart', function() {

        return {

            // required to make it work as an element
            restrict: 'E',
            template: '<div></div>',
            replace: true,
            // observe and manipulate the DOM
            link: function($scope, element, attrs) {

                var data = $scope[attrs.data],
                    xkey = $scope[attrs.xkey],
                    ykeys= $scope[attrs.ykeys],
                    labels= $scope[attrs.labels];

                Morris.Bar({
                        element: element,
                        data: data,
                        xkey: xkey,
                        ykeys: ykeys,
                        labels: labels
                    });

            }

        };

    });

    // Global stuff
    module.directive('active', function ($location) {
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

    module.service('SourceCodeService', function ($http) {
        this.getSourceCode = function (path) {
            return $http.get('http://192.168.0.212:8080source?path=' + path);
        };
    });

    module.directive('sourceCode', function (SourceCodeService, $timeout) {
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
    module.service('MaquinaService', function ($http) {
    	this.getAll = function () {
            return $http.get('http://192.168.0.212:8080/maquinas/search/findAllByAtivo?ativo=true');
        }
    	this.get = function (codigo) {
            return $http.get('http://192.168.0.212:8080/maquinas/' + codigo);
        }
        this.getLog = function (codigo) {
            return $http.get('http://192.168.0.212:8080/maquinaLogs/search/findAllByMaquina?maquina=' + codigo + '&page=1&size=20&sort=datahora,desc');
        }
        this.getSumario = function(codigo) {
            return $http.get('http://192.168.0.212:8080/sumarios/search/findByMaquina?maquina=' + codigo);
        }
    });
    
    module.controller('MaquinaCtrl', ['MaquinaService', '$scope','$routeParams', function (MaquinaService, $scope, $routeParams) {
        var self = this;
        self.data = {};
        self.total = 0;  

        $scope.xkey = 'descricao';

        $scope.ykeys = ['percentual'];

        $scope.labels = ['%'];

        $scope.myModel = [
        /*{ descricao: 'Ociosa', tempo: 20 },
        { descricao: 'Preparacao', tempo: 35 },
        { descricao: 'Producao', tempo: 20 },
        { descricao: 'Manual', tempo: 20 }*/
        ];

        function refresh() {
            MaquinaService.get($routeParams.codigo).then(function (response) {
                self.data = response.data;
                self.data.log = [];
                self.data.sumario = [];
                MaquinaService.getLog($routeParams.codigo).then(function (response) {
                    if (response.data.page.totalElements > 0) {
                        self.data.log = response.data._embedded.maquinaLogs;    
                    }
                });
                MaquinaService.getSumario($routeParams.codigo).then(function (response) {
                    if (response.data._embedded) {
                        $scope.myModel = response.data._embedded.sumarios; 
                        self.data.sumario = response.data._embedded.sumarios; 
                        for( var i = 0; i < self.data.sumario.length; ++i ) {
                            /*if (self.data.sumario[i].tempo > 60 * 60 * 10) {
                                self.data.sumario[i].tempo = 60 * 60 * 10;
                            }*/
                            self.total += self.data.sumario[i].tempo;
                        }
                        for( var i = 0; i < self.data.sumario.length; ++i ) {
                             self.data.sumario[i].percentual = (self.data.sumario[i].tempo / self.total) * 100;
                             self.data.sumario[i].percentual = self.data.sumario[i].percentual.toFixed(2);
                        }
                    }
                });
            });
        }

        refresh();

        function initView() {

            var sock = new SockJS('http://192.168.0.212:8080/api/notify');
            sock.onmessage = function (response) {
                var msg = JSON.parse(response.data);

                if (self.data.codigo == msg.data.maquina) {
                    self.data.tempo = msg.data.tempo;
                    self.data.situacao = msg.data.modo;
                    self.data.operador = msg.data.operador;

                    self.data.log.reverse().push(msg.data);

                    if (self.data.log.length > 20) {
                        self.data.log = self.data.log.slice(self.data.log.length - 20);
                    }

                    self.data.log = self.data.log.reverse();

                    $scope.$apply();
                }

            };
        }

        initView();          
    }]);
    
    // menu services
    module.controller('MenuCtrl', function (MaquinaService, $scope) {
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

            var sock = new SockJS('http://192.168.0.212:8080/api/notify');
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
    module.service('SqsService', function ($http) {
        this.sendMessage = function (message) {
            return $http.post('sqs/message-processing-queue', message);
        };
    });

    module.controller('SqsCtrl', function (SqsService, $scope) {
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

            var sock = new SockJS('http://192.168.0.212:8080/notify');
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

    module.filter('statusClass', function () {
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
            case 99: return 'danger';
            }
        }
    });

    module.filter('statusPanelClass', function () {
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
            case 99: return 'panel-danger';
            }
        }
    });   

    module.filter('statusName', function () {
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

    module.filter('statusLabelClass', function () {
        return function (input) {
            switch (input) {
            case 0: return 'label-danger';
            case 1: return 'label-success';
            case 2: return 'label-info';
            case 3: return 'label-info';
            case 4: return 'label-info';
            case 5: return 'label-danger';
            case 6: return 'label-success';
            case 7: return 'label-warning';
            case 99: return 'label-danger';
            }
        }
    }); 

    module.filter('priority', function () {
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
    module.service('SnsService', function ($http) {
        this.send = function (message) {
            return $http.post('sns/send', message);
        };
    });

    module.controller('SnsCtrl', function (SnsService, $scope) {
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

            var sock = new SockJS('http://192.168.0.212:8080/sns-messages');
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
    module.service('PersonService', function ($http) {
        this.add = function (person) {
            return $http.post('persons', person);
        };

        this.getAll = function () {
            return $http.get('http://192.168.0.212:8080persons');
        }
    });

    module.controller('RdsCtrl', function (PersonService) {
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
    module.service('ElastiCacheService', function ($http) {
        this.getValue = function (key) {
            return $http.get('http://192.168.0.212:8080cachedService?key=' + key, {headers: {Accept: 'text/plain'}});
        };
    });

    module.controller('ElastiCacheCtrl', function ($scope, ElastiCacheService) {
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
    module.service('Ec2Service', function ($http) {
        this.getProperties = function () {
            return $http.get('http://192.168.0.212:8080info');
        };
    });

    module.controller('Ec2Ctrl', function (Ec2Service) {
        var self = this;

        Ec2Service.getProperties().then(function (response) {
            self.properties = response.data;
        });
    });

    module.controller('DashboardCtrl', ['MaquinaService', '$scope','$routeParams', function (MaquinaService, $scope, $routeParams) {
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

            var sock = new SockJS('http://192.168.0.212:8080http://127.0.0.1:8080/api/notify');
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
    
    module.config(function ($routeProvider) {
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