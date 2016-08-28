<!DOCTYPE html>
<html ng-app="tasksApp">
    <head>
        <title>Index</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-animate.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-sanitize.js"></script>
        <script src="js/sockjs.js"></script>
        <script src="js/stomp.js"></script>
        <script src="js/ui-bootstrap-tpls-2.1.3.min.js"></script>
        <script src="js/app.js?timestamp=${timestamp?c}"></script>
        
        </head>
    <body>
        <div><h6>Version: ${version} ${datetime}</h6></div>
        <div ng-controller="TasksController as tasks" class="container">
            <button class="btn btn-primary" ng-click="tasks.open()">Добавить</button>{{tasks.saved}}
            <h3>Задачи</h3>
            <table class="table">
                <tr>
                    <th>Идентификатор</th>
                    <th>Описание</th>
                    <th>Путь</th>
                    <th>имя логгера</th>
                    <th>расписание</th>
                    <th>Включено</th>
                    <th>Действие</th>
                    </tr>
                <tr ng-repeat="t in tasks.list">
                    <td>{{t.id}}</td>
                    <td>{{t.description}}</td>
                    <td>{{t.path}}</td>
                    <td>{{t.loggerName}}</td>
                    <td>{{t.scheduler}}</td>
                    <td>{{t}}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" ng-click="tasks.run(t)"><span class="glyphicon glyphicon-play" aria-hidden="true"></span></button>
                        <button class="btn btn-primary btn-sm" ng-click="tasks.open(t)"><span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span></button>
                    </td>
                    </tr>
                </table>
            
            
            
            


    <script type="text/ng-template" id="myModalContent.html">
        <div class="modal-header">
            <h3 class="modal-title" id="modal-title">Добавление задачи</h3>
        </div>
        <div class="modal-body" id="modal-body">
            <table width="100%">
            <tr><td width="20%">Описание</td><td width="80%"><input type='text' class="form-control" ng-model='$ctrl.item.description'></td></tr>
            <tr><td>Путь</td><td><input type='text' class="form-control" ng-model='$ctrl.item.path'></td></tr>
            <tr><td>Имя логгера</td><td><input type='text' class="form-control" ng-model='$ctrl.item.loggerName'></td></tr>
            <tr><td>Расписание</td><td><input type='text' class="form-control" ng-model='$ctrl.item.scheduler'></td></tr>
            <tr><td>Включено</td><td><input type='checkbox' class="" ng-model='$ctrl.item.enabled'></td></tr>
            </table>
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" type="button" ng-click="$ctrl.ok()">OK</button>
            <button class="btn btn-warning" type="button" ng-click="$ctrl.cancel()">Cancel</button>
        </div>
    </script>


</div>



        </body>
    </html>
