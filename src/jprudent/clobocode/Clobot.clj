(ns jprudent.clobocode.Clobot
  "A sample robot that does nothing but the boilerplate.
  Copy/pasting this file is a good way to start a robot of your own."
  (:gen-class :extends robocode.AdvancedRobot
              :implements [clojure.lang.IDeref]
              :state robot
              :init init
              :methods [[run [robocode.AdvancedRobot] void]])
  (:import (robocode AdvancedRobot))
  (:require [jprudent.clobocode.adapter :refer [run]]
            [jprudent.clobocode.api :refer [robot-factory]]))

;; This is where you put your logic :

(defn- -onLoop [robot]
  (println "Here I am : " robot)
  (println "I should toy with my gun and stuff but I'm sleepy."))

(defn- -deref [this]
  @(.robot this))

(defn- -init []
  [[] (atom (robot-factory true))])

(defn- -run [this]
  (run this -onLoop))