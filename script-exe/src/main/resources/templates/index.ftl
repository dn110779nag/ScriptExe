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
    <body>
        <div><h6>Version: ${version} ${datetime}</h6></div>
        <div ng-controller="TasksController as tasks" class="container">
            <button class="btn btn-primary">Добавить</button>
            <h3>Задачи</h3>
            <table class="table">
                <tr>
                <th>id</th>
                <th>description</th>
                <th>path</th>
                <th>loggerName</th>
                <th>scheduler</th>
                <th>enabled</th>
                <th>run</th>
                </tr>
                <tr ng-repeat="t in tasks.list">
                    <td>{{t.id}}</td>
                    <td>{{t.description}}</td>
                    <td>{{t.path}}</td>
                    <td>{{t.loggerName}}</td>
                    <td>{{t.scheduler}}</td>
                    <td>{{t.enabled}}</td>
                    <td><button class="btn btn-warning btn-sm"><span class="glyphicon glyphicon-play" aria-hidden="true"></span></button></td>
                </tr>
            </table>
        </body>
    </html>
