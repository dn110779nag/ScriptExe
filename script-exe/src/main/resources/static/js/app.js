//alert("hello");
//console.log("test");
angular.module('tasksApp', ['ngAnimate', 'ngSanitize', 'ui.bootstrap'])
//angular.module("tasksApp", [])
        .controller("TasksController", ['$http', '$uibModal', function ($http, $uibModal) {
                var tasks = this;

                var socket = new SockJS("/statuses");
                var stompClient = Stomp.over(socket);
                
                tasks.change = function(sts){
                    var index = tasks.idIndex[""+sts.id];
                    tasks.list[index].running = sts.running;
                }
                
                stompClient.connect({}, function (frame) {
                    console.log('!!!Connected ' + frame);
                    stompClient.subscribe("/queue/statuses", function (message) {
                        //console.log(JSON.parse(message.body));
                        var sts = JSON.parse(message.body);
                        tasks.change(sts)
                        //console.log("tasks.idIndex=>"+JSON.stringify(tasks.idIndex));
                        //console.log("message.body=>"+sts.id);
                        //tasks.idIndex[""+sts.id].running = sts.running;
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
                    for(var i=0; i<tasks.list.length; i++ ){
                        tasks.idIndex[""+tasks.list[i].id] = i;
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

                tasks.changeStatus = function (item) {
                    $http({
                        method: 'POST',
                        url: '/task/status/' + item.id,
                        data: {status: item.enabled}
                    }).then(function (response) {
                        //Должно быть тру/фалс, но все по сокету получим
                        //tasks.status = response.data;
                    });
                };

                tasks.open = function (item) {
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
                                return item || {};
                            }
                        }
                    });

                    modalInstance.result.then(function (selectedItem) {
                        tasks.list.push(selectedItem);
                    }, function () {
                        console.log('Modal dismissed at: ' + new Date());
                    });
                };



            }]);

angular.module('tasksApp').controller('ModalInstanceCtrl', function ($uibModalInstance, $http, item) {
    var $ctrl = this;
    $ctrl.item = item;
    $ctrl.ok = function () {
        //save
        $http({
            method: 'POST',
            url: '/task',
            data: $ctrl.item
        }).then(function (response) {
            $ctrl.item = response.data;
            $uibModalInstance.close($ctrl.item);
        });

    };

    $ctrl.cancel = function () {
        console.log(111);
        $uibModalInstance.dismiss('cancel');
    };
});


