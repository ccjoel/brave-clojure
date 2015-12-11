(defproject clojure-test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]]
  :main ^:skip-aot clojure-test.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}

  :test-refresh {;; Specifies a command to run on test
                   ;; failure/success. Short message is passed as the
                   ;; last argument to the command.
                   ;; Defaults to no command.
                   :notify-command ["notify-send"]

                   ;; set to true to send notifications to growl
                   ;; Defaults to false.
                   :growl true

                   ;; only growl and use the notify command if there are
                   ;; failures.
                   ;; Defaults to true.
                   :notify-on-success true

                   ;; Stop clojure.test from printing
                   ;; "Testing namespace.being.tested". Very useful on
                   ;; codebases with many test namespaces.
                   ;; Defaults to false.
                   :quiet false

                   ;; specifiy a custom clojure.test report method
                   ;; Specify the namespace and multimethod that will handle reporting
                   ;; from test-refresh.  The namespace must be available to the project dependencies.
                   ;; Defaults to no custom reporter
                   ;:report  myreport.namespace/my-report
                   })
