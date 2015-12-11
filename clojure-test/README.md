# clojure-test

To RUN:

```
lein run
```

To Build:
```
lein uberjar
```

To run built program:
```
java -jar target/uberjar/clojure-test-0.1.0-SNAPSHOT-standalone.jar
```

Running repl for faster tests:

On project root dir ($path/clojure-test/), run:
```
lein repl
```

To test:

```
lein test
```

to test with watcher and auto retest:
```
lein test-refresh
```

Will see something like:
nREPL server started on port 28925
REPL-y 0.1.10
Clojure 1.7.0
    Exit: Control+D or (exit) or (quit)
Commands: (user/help)
    Docs: (doc function-name-here)
          (find-doc "part-of-name-here")
  Source: (source function-name-here)
          (user/sourcery function-name-here)
 Javadoc: (javadoc java-object-or-class-here)
Examples from clojuredocs.org: [clojuredocs or cdoc]
          (user/clojuredocs name-here)
          (user/clojuredocs "ns-here" "name-here")


then you can open vim on project root as well, and it will connect to this
REPL automagically.

## Installation

Download from http://example.com/FIXME.

## Usage

FIXME: explanation

    $ java -jar clojure-test-0.1.0-standalone.jar [args]

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
