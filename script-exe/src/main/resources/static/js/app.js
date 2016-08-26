//alert("hello");
console.log("test");

angular.module("tasksApp", [])
    .controller("TasksController", ['$http', function ($http) {
        var tasks = this;
        tasks.greeting = "angular linked";
        
        $http({
            method: 'GET',
            url: '/task'
        }).then(function (response){
            tasks.list = response.data;
        });
        

    }]);

