# robocodebots

Some bots written in Clojure for Robocode

## Build

Download and install Leiningen, then :

    lein jar

a jar containing the bots is produced in `/target`

## Usage

Launch Robocode :

    java -cp ~/.m2/repository/org/clojure/clojure/1.5.1/clojure-1.5.1.jar:./robocode.jar -Xmx512M -Dsun.io.useCanonCaches=false -DNOSECURITY=true robocode.Robocode
    
Then import the jar generated with lein.

## License

Public Domain