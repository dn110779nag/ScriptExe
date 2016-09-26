PIDFILE=pid
pid=$(cat "$PIDFILE")
kill "$pid"
