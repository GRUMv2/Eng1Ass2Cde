```plantuml
class Server {
	+Pause()
	+Resume()
	+isPaused() : Bool
	+Stop()
}
```

### Server's main loop;

```plantuml
start

repeat
	:tick();
	:get current time\ncalculate delay\nwait delay;
repeat while (isRunning) is (True)
->False;

stop

```

### Tick;
```plantuml
start



stop
```
