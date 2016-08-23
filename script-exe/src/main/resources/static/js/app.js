//alert("hello");
console.log("test");

angular.module("tasksApp", [])
        .controller("TasksController", function () {
            var tasks = this;
            tasks.greeting = "angular linked";
        });

