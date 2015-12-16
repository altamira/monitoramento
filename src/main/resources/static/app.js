/* global angular.js */
(function () {
    var module = angular.module('monitor', ['ngRoute']);
                     
    module.directive('barchart', function ($window) {

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
                        //console.log('creating chart');
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
                                //console.log("--> "+row.label, row.x, series, type);
                                if(row.label == 'Parada Indeterminada') return "#e66454";
                                else if(row.label == 'Ociosa') return "#5ebd5e";
                                else if(row.label == 'Produzindo') return "#5bc0de";
                                else if(row.label == 'Produzindo (Ajuste Manual)') return "#5bc0de";
                                else if(row.label == 'Preparacao') return "#5bc0de";
                                else if(row.label == 'Manutencao') return "#e66454";
                                else if(row.label == 'Maquina Desligada') return "#5ebd5e";
                                else if(row.label == 'IHM Desligada') return "#f4b04f";
                                else if(row.label == 'Falha Comunicacao') return "#e66454";
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

    // maquina services
    module.service('MaquinaService', function ($http) {
    	this.getAll = function () {
            return $http.get('/maquinas/search/findAllByAtivo?ativo=true');
        }
    	this.get = function (codigo) {
            return $http.get('/maquinas/' + codigo);
        }
        this.getLog = function (codigo) {
            return $http.get('/maquinaLogs/search/findAllByMaquina?maquina=' + codigo + '&page=0&size=20&sort=datahora,sequencia,desc');
        }
        this.getSumario = function(codigo) {
            return $http.get('/sumarios/search/findByMaquina?maquina=' + codigo);
        }
        this.getSumarioAll = function() {
            return $http.get('/sumarios/search/findAll');
        }
        this.getIHM = function(codigo) {
            return $http.get('/iHMs/search/findAllByMaquina?maquina=' + codigo);
        }
    });

    // menu services
    module.controller('MenuCtrl', function (MaquinaService, $scope) {
    	var self = this;
        self.options = [];
        self.count = 0;

        function refresh() {
        	MaquinaService.getAll().then(function (response) {
                self.options = response.data._embedded.maquinas;
                for( var i = 0; i < self.options.length; ++i ) {
                	
                	if (self.options[i].falhaComunicacao > 0) {
                		self.options[i].tempo = self.options[i].falhaComunicacao;
                		self.options[i].situacao = 99;
                	}
                	
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
                    var hora = parseInt(horas % 24, 10);
                    var dias = parseInt(horas / 24, 10); 
                    var dia = parseInt(dias, 10);
                    
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
                        var hora = parseInt(horas % 24, 10);
                        var dias = parseInt(horas / 24, 10); 
                        var dia = parseInt(dias, 10);
                        
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

    module.controller('DashboardCtrl', ['MaquinaService', '$scope','$routeParams', function (MaquinaService, $scope, $routeParams) {
        var self = this;
        self.maquinas = [];
        self.count = 0;

        function refresh() {
            MaquinaService.getAll().then(function (response) {
                self.maquinas = response.data._embedded.maquinas;

                for( var i = 0; i < self.maquinas.length; ++i ) {

                	if (self.maquinas[i].falhaComunicacao > 0) {
                		self.maquinas[i].situacao = 99;
                		self.maquinas[i].tempo = self.maquinas[i].falhaComunicacao;
                	} 
                	
                    var segundos = parseInt(self.maquinas[i].tempo, 10); 
                    var segundo = parseInt(segundos % 60, 10); 
                    var minutos = parseInt(segundos / 60, 10); 
                    var minuto = parseInt(minutos % 60, 10); 
                    var horas = parseInt(minutos / 60, 10);
                    var hora = parseInt(horas % 24, 10);
                    var dias = parseInt(horas / 24, 10); 
                    var dia = parseInt(dias, 10);
                    
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

        self.data = {};
        self.total = 0;  

        $scope.xkey = 'descricao';

        $scope.ykeys = ['percentual'];

        $scope.labels = ['%'];

        $scope.myModel = [];
        
        function updateGraph() {
            self.data.sumario = [];
            MaquinaService.getSumarioAll().then(function (response) {
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
        }

        updateGraph();
        
        function initView() {

            var sock = new SockJS('/api/notify');
            sock.onmessage = function (response) {
                var msg = JSON.parse(response.data);

                for( var i = 0; i < self.maquinas.length; ++i ) {
                    if (self.maquinas[i].codigo == msg.data.maquina) {

                    	self.maquinas[i].situacao = msg.data.modo;
                        self.maquinas[i].operador = msg.data.operador;

                        var segundos = parseInt(msg.data.tempo, 10); 
                        var segundo = parseInt(segundos % 60, 10); 
                        var minutos = parseInt(segundos / 60, 10); 
                        var minuto = parseInt(minutos % 60, 10); 
                        var horas = parseInt(minutos / 60, 10);
                        var hora = parseInt(horas % 24, 10);
                        var dias = parseInt(horas / 24, 10); 
                        var dia = parseInt(dias, 10);
                        
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
        
    module.controller('MaquinaCtrl', ['MaquinaService', '$scope','$routeParams', function (MaquinaService, $scope, $routeParams) {
        var self = this;
        self.data = {};
        self.total = 0;  

        $scope.xkey = 'descricao';

        $scope.ykeys = ['percentual'];

        $scope.labels = ['%'];

        $scope.myModel = [];

        function refresh() {
            MaquinaService.get($routeParams.codigo).then(function (response) {
                self.data = response.data;
                self.data.log = [];
                self.data.sumario = [];
                self.data.ihm = [];
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
                MaquinaService.getIHM($routeParams.codigo).then(function (response) {
                    self.data.ihm = response.data._embedded.iHMs; 
                });
            });
        }

        refresh();

        function initView() {

            var sock = new SockJS('/api/notify');
            sock.onmessage = function (response) {
                var msg = JSON.parse(response.data);

                if (self.data.codigo == msg.data.maquina) {
                	
                    self.data.situacao = msg.data.modo;
            		self.data.tempo = msg.data.tempo;
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
            case 0: return 'Parada Indeterminada';
            case 1: return 'Ociosa';
            case 2: return 'Produzindo';
            case 3: return 'Produzindo (Ajuste Manual)';
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

    module.filter('timeFormat', function () {
        return function (input) {
            var segundos = parseInt(input, 10); 
            var segundo = parseInt(segundos % 60, 10); 
            var minutos = parseInt(segundos / 60, 10); 
            var minuto = parseInt(minutos % 60, 10); 
            var horas = parseInt(minutos / 60, 10);
            var hora = parseInt(horas % 24, 10);
            var dias = parseInt(horas / 24, 10); 
            var dia = parseInt(dias, 10);
            
            var format = '';

            /*
            if (dia > 1) {
                format = dia + ' dias '
            } else if (dia == 1) {
                format = dia + ' dia '
            } */
            
            format += horas + ':' + ("0" + minuto).slice(-2) + ':' + ("0" + segundo).slice(-2) + ' h';
            
            /*if (hora > 0) {
                format += hora + ' h '
            } 
            if (minuto > 0) {
                format += minuto + ' min '
            } 
            if (segundo > 0) {
                format += segundo + ' s';
            }
            format += ' [' + input + ']'*/

            return format;
        }
    });

    module.config(function ($routeProvider) {
        $routeProvider.when('/home', {templateUrl: '/pages/home.tpl.html'});
        $routeProvider.when('/index', {templateUrl: '/template/index.html'});
        $routeProvider.when('/main', {templateUrl: '/pages/main.tpl.html'});
        $routeProvider.when('/dashboard', {templateUrl: '/pages/dashboard.html'});
        $routeProvider.when('/maquina/:codigo', {templateUrl: '/pages/maquina.html'});
        $routeProvider.otherwise({redirectTo: '/dashboard'});
    });
}());