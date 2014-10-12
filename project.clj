(defproject robocodebots "0.1.0-SNAPSHOT"
            :description "FIXME: write description"
            :url "http://example.com/FIXME"
            :license {:name "Eclipse Public License"
                      :url  "http://www.eclipse.org/legal/epl-v10.html"}
            :dependencies [[org.clojure/clojure "1.5.1"]
                           [robocode/robocode "1.9.2.3"]]
            :aot [jprudent.clobocode.Clobot jprudent.Tetanized]
            :manifest ["Robots" "jprudent.Tetanized, jprudent.clobocode.Clobot"])
