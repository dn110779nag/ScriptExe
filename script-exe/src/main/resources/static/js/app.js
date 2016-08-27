//alert("hello");
//console.log("test");
angular.module('tasksApp', ['ngAnimate', 'ngSanitize', 'ui.bootstrap'])
//angular.module("tasksApp", [])
        .controller("TasksController", ['$http', '$uibModal', function ($http, $uibModal) {
                var tasks = this;
                

                $http({
                    method: 'GET',
                    url: '/task'
                }).then(function (response) {
                    tasks.list = response.data;
                });

                tasks.run = function(item){
                    $http({
                        method: 'GET',
                        url: '/task/run/'+item.id
                    }).then(function (response) {
                        tasks.list = response.data;
                    });
                };
                
                tasks.changeStatus = function(item){
                    $http({
                        method: 'POST',
                        url: '/task/status/'+item.id,
                        data: {status: item.enabled}
                    }).then(function (response) {
                        tasks.list = response.data;
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
                                return item||{};
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


