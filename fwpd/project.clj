(defproject fwpd "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]]
  :main ^:skip-aot fwpd.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :test-refresh {
                 ;; Specifies a command to run on test
                 :notify-command ["notify-send"]
                 :growl true
                 :notify-on-success true
                 :quiet false
                 ;:report  myreport.namespace/my-report
                 })
