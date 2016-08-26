<!DOCTYPE html>
<html ng-app="tasksApp">
    <head>
        <title>Index</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular.min.js"></script>
        <script src="js/ui-bootstrap-tpls-2.1.3.min.js"></script>
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
            
            
            
            


    <script type="text/ng-template" id="myModalContent.html">
        <div class="modal-header">
            <h3 class="modal-title" id="modal-title">I'm a modal!</h3>
        </div>
        <div class="modal-body" id="modal-body">
            <ul>
                <li ng-repeat="item in $ctrl.items">
                    <a href="#" ng-click="$event.preventDefault(); $ctrl.selected.item = item">{{ item }}</a>
                </li>
            </ul>
            Selected: <b>{{ $ctrl.selected.item }}</b>
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" type="button" ng-click="$ctrl.ok()">OK</button>
            <button class="btn btn-warning" type="button" ng-click="$ctrl.cancel()">Cancel</button>
        </div>
    </script>


</div>



        </body>
    </html>
