<!DOCTYPE html>
<html ng-app="tasksApp">
    <head>
        <title>Index</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular.min.js"></script>
        <script src="js/app.js?timestamp=${timestamp?c}"></script>
        </head>
    <body >
        <button>Добавить</button>
        <#if !name??><div>Just hello</div></#if>
        <#if name??><div>Hello ${name}</div></#if>
        <div ng-controller="TasksController as tasks">
            <h1>t{{tasks.greeting}}</h1>

            <label>Name:</label>
            <input type="text" ng-model="yourName" placeholder="Enter a name here">
            <hr>
            <h1>Hello {{yourName}}!</h1>
            </div>
        </body>
    </html>
