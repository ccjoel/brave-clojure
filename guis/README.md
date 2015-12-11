# guis

FIXME: description

### Fast running lein run / repl with trampoline:
```
LEIN_FAST_TRAMPOLINE=y lein trampoline run -m guis.core
```

### Fast running lein with drip:
```
lein run -m guis.core
```

but only if drip is defined and `LEIN_JAVA_CMD` is exported pointing
to where the drip command is. Uses JVM to boot it faster for lein,
and for running an app.

### Run lein repl:
```
lein repl
```

### Run normal clojure repl:
```
LEIN_FAST_TRAMPOLINE=y lein trampoline run -m clojure.main
```

## Installation

Download from http://example.com/FIXME.

## Usage

FIXME: explanation

    $ java -jar guis-0.1.0-standalone.jar [args]

## Options

FIXME: listing of options this app accepts.

## Examples

...

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2015 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
