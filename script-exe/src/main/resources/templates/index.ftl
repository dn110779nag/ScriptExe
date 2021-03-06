<!DOCTYPE html>
<html ng-app="tasksApp">
    <head>
        <title>ScriptExe</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-animate.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-sanitize.js"></script>
        <script src="js/moment.min.js"></script>
        <script src="js/sockjs.js"></script>
        <script src="js/stomp.js"></script>
        <script src="js/ui-bootstrap-tpls-2.1.3.min.js"></script>
        <script>
            var csrfToken = "${_csrf.token}";
            var csrfParam = "${_csrf.parameterName}";
        </script>
        <script src="js/app.js?timestamp=${timestamp?c}"></script>
        
        <style>
            .header{
                display: block;
                font-size: 32px;
            }
            .subheader{
                display: block;
                font-size: 12px;
                color: grey;
                font-family: Courier;
                font-weight: bold;
            }
            td{
                font-size: 14px;
                font-family: Arial;
            }
            td.actions{
                min-width: 160px;
            }
            .date{
                font-size: 14px;
                font-family: Courier;
                font-weight: bold;
            }
            
            
        </style>
        </head>
    <body>
        
        <div class="container">
            <div class="row">
                <div class="col-md-2"></div>
                <div class="col-md-1"><img src="images/icon128-2x.png" width="64px" height="64px" class="img-circle"></div>
                <div class="col-md-9">
                    <span class="header">ScriptExe - выполнение скриптов</span>
                    <span class="subheader">Сборка: ${version} ${datetime}</span>
                </div>
            </div>
        </div>
        <div ng-controller="TasksController as tasks" class="container-fluid">
            <button class="btn btn-primary" ng-click="tasks.open(null,'add')" title='Добавление задачи'>Добавить</button>
            <h3>Задачи</h3>
            
            <table class="table">
                <tr>
                    <th><input type="text" ng-model="filter.id"></th>
                    <th><input type="text" ng-model="filter.description"></th>
                    <th><input type="text" ng-model="filter.path"></th>
                    <th><input type="text" ng-model="filter.loggerName"></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
                <tr>
                    <th>Идентификатор</th>
                    <th>Описание</th>
                    <th>Путь</th>
                    <th>имя логгера</th>
                    <th>расписание</th>
                    <th>Включено</th>
                    <th>Запущено</th>
                    <th>Следующий старт</th>
                    <th>Дата и статус последней отработки</th>
                    <th>Действие</th>
                </tr>
                <tr ng-repeat="t in tasks.list | filter:filter">
                    <td>{{t.id}}</td>
                    <td>{{t.description}}</td>
                    <td>{{t.path}}</td>
                    <td>{{t.loggerName}}</td>
                    <td>{{t.scheduler}}</td>
                    <td><input disabled type="checkbox" ng-model="t.enabled"></td>
                    <td><input disabled readonly type="checkbox" ng-model="t.running"><br><span title="Дата последнего старта" class="date">{{t.lastStart}}</span></td>
                    <td><span class="date">{{t.nextStart}}</span></td>
                    <td><span class="date">{{t.lastFinish}}</span><br><span title="Последняя ошибка">[{{t.lastError}}]</span></td>
                    <td class="actions">
                        <button class="btn btn-primary btn-sm" ng-click="tasks.open(t, 'copy')" title="Копировать"><span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span></button>
                        <button class="btn btn-primary btn-sm" ng-click="tasks.open(t, 'modification')" title="Изменить параметры задачи"><span class="glyphicon glyphicon-wrench" aria-hidden="true"></span></button>
                        <button class="btn btn-warning btn-sm" ng-click="tasks.run(t)" title="Запустить задачу немедленно. (Предварительно должно быть включено выполнение по расписанию)"><span class="glyphicon glyphicon-play" aria-hidden="true"></span></button>
                        <button class="btn btn-warning btn-sm" ng-click="tasks.changeStatus(t, true)" title="Включить выполнение задачи по расписанию"><span class="glyphicon glyphicon-time" aria-hidden="true"></span></button>
                        <button class="btn btn-default btn-sm" ng-click="tasks.changeStatus(t, false)" title="Отключить выполнение задачи по расписанию"><span class="glyphicon glyphicon-pause" aria-hidden="true"></span></button>
                    </td>
                    </tr>
                </table>
            
            
            
            


    <script type="text/ng-template" id="myModalContent.html">
        <div class="modal-header">
            <h3 class="modal-title" id="modal-title">{{$ctrl.caption}}</h3>
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
