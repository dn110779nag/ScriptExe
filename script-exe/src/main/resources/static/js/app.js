//alert("hello");
//console.log("test");
angular.module('tasksApp', ['ngAnimate', 'ngSanitize', 'ui.bootstrap'])
//angular.module("tasksApp", [])
        .controller("TasksController", ['$http', '$uibModal', '$q', function ($http, $uibModal, $q) {
                var tasks = this;

                tasks.socket = new SockJS("/statuses");
                tasks.stompClient = Stomp.over(tasks.socket);
                tasks.stompClient.debug = function () {};

                function sockDataFetching(data) {
                    var deferred = $q.defer();
                    setTimeout(function () {
                        deferred.resolve(data);
                    }, 1000);
                    return deferred.promise;
                }


                function formatDate(d) {
                    if (!d)
                        return null;
                    else {
                        var dd = new Date(d);
                        return moment(dd).format("YYYY-MM-DD HH:mm:ss");
                    }
                }

                tasks.change = function (sts) {
                    var index = tasks.idIndex["" + sts.id];
                    var t = tasks.list[index];
                    t.running = sts.running;
                    t.enabled = sts.enabled;
                    t.lastStart = formatDate(sts.lastStart);
                    t.lastFinish = formatDate(sts.lastFinish);
                    t.lastError = sts.lastError;
                    t.nextStart = formatDate(sts.nextStart);
                    //console.log(tasks.list[index]);
                };

                tasks.stompClient.connect({}, function (frame) {
                    console.log('!!!Connected ' + frame);
                    tasks.stompClient.subscribe("/queue/statuses", function (message) {
                        //console.log(JSON.parse(message.body));
                        var sts = JSON.parse(message.body);
                        var promise = sockDataFetching(sts);
                        promise.then(function (data) {
                            tasks.change(sts);
                        });
                    });
                }, function (error) {
                    console.log("STOMP protocol error " + error);
                });




                $http({
                    method: 'GET',
                    url: '/task'
                }).then(function (response) {
                    tasks.list = response.data;
                    tasks.idIndex = {};
                    for (var i = 0; i < tasks.list.length; i++) {
                        tasks.idIndex["" + tasks.list[i].id] = i;
                    }


                });

                tasks.run = function (item) {
                    $http({
                        method: 'GET',
                        url: '/task/run/' + item.id
                    }).then(function (response) {
                        //Должно быть тру/фалс, но все по сокету получим
                        //tasks.status = response.data;
                    });
                };

                tasks.changeStatus = function (item, newStatus) {
                    $http({
                        method: 'POST',
                        url: '/task/status/' + item.id + "?" + csrfParam + "=" + csrfToken,
                        data: {status: newStatus}
                    }).then(function (response) {
                        //Должно быть тру/фалс, но все по сокету получим
                        //tasks.status = response.data;
                    });
                };

                tasks.open = function (item, mode) {
                    var modalInstance = $uibModal.open({
                        animation: true,
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: 'myModalContent.html',
                        controller: 'ModalInstanceCtrl',
                        controllerAs: '$ctrl',
                        size: 'lg',
                        resolve: {
                            item: function () {
                                return {
                                    item: item || {},
                                    mode: mode
                                };
                            }
                        }
                    });

                    modalInstance.result.then(function (res) {
                        var s = res.item;
                        if(res.method=="POST"){
                            tasks.list.push(s);
                        } else {
                            var index = tasks.idIndex["" + s.id];
                            var t = tasks.list[index];
                            s.description = t.description;
                            s.path = t.path;
                            s.loggerName = t.loggerName;
                            s.enabled = t.enabled;
                            s.scheduler = t.scheduler;
                        }
                    }, function () {
                        console.log('Modal dismissed at: ' + new Date());
                    });
                };



            }]);

angular.module('tasksApp').controller('ModalInstanceCtrl', function ($uibModalInstance, $http, item) {
    var $ctrl = this;
    $ctrl.item = item.item;
    $ctrl.mode = item.mode;
    $ctrl.caption = {
        "add":"Добавление задачи", 
        "copy":"Копирование задачи", 
        "modification": "Изменение задачи"
    }[$ctrl.mode];
    $ctrl.method = $ctrl.mode==="modification"?"PUT":"POST";
    
    $ctrl.ok = function () {
        //save
        $http({
            method: $ctrl.method,
            url: '/task?' + csrfParam + "=" + csrfToken,
            data: $ctrl.item
        }).then(function (response) {
            $ctrl.item = response.data;
            $uibModalInstance.close({item: $ctrl.item, method: $ctrl.method});
        });

    };

    $ctrl.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});


