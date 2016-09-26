PIDFILE=pid
LOGFILE=app.log

pid=
#проверка есть ли файл в пидом, если есть, то попытаться погасить приложение
if [ -f "$PIDFILE" ]; then
 pid=$(cat "$PIDFILE")
 if [ -n "$pid" ];  then
  echo stopping $pid
  kill "$pid"
 fi
fi


#echo $$ > "$PIDFILE"
#-Dloader.config.location=./config/override.properties \

java \
-Dloader.path=./lib,./scripts \
-Dlog4j.configurationFile=config/log4j2.xml \
-Dspring.config.location=file:config/override.properties \
-Dserver.port=2001 \
-jar script-exe-1.0.jar >> "$LOGFILE" 2>&1 &

pid=$!
echo pid="$pid"
#disown "$pid"
echo "$pid" > "$PIDFILE"
pid=$(cat "$PIDFILE")
echo starting $pid