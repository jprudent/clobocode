(ns jprudent.Tetanized
  "A sample robot that does nothing but the boilerplate.
  Copy/pasting this file is a good way to start a robot of your own."
  (:gen-class :extends robocode.AdvancedRobot
              :implements [clojure.lang.IDeref]
              :state robot
              :init init
              :methods [[run [robocode.AdvancedRobot] void]])
  (:import (robocode AdvancedRobot))
  (:require [jprudent.clobocode.adapter :refer [run]]
            [jprudent.clobocode.math :refer :all]
            [jprudent.clobocode.api :refer :all]))

(defn- -onLoop [{:keys [gun radar] :as robot}]
  (-> robot
      (assoc :gun (head-to gun PI))
      (assoc :radar (head-to radar PI÷2))))

(defn- -deref [this]
  @(.robot this))

(defn- -init []
  [[] (atom (robot-factory true))])

(defn- -run [this]
  (run this -onLoop))