(ns jprudent.clobocode.adapter
  (:require [clojure.data :refer [diff]]
            [clojure.pprint :refer [pprint]])
  (:import (robocode AdvancedRobot Rules)))

(defn- turn-gun! [^AdvancedRobot this targeted-bearing]
  (let [delta (- (.getGunHeadingRadians this) targeted-bearing)]
    (if (pos? delta)
      (.setTurnGunLeftRadians this delta)
      (.setTurnGunRightRadians this delta))))

(defn- turn-radar! [^AdvancedRobot this targeted-bearing]
  (let [delta (- (.getRadarHeadingRadians this) targeted-bearing)]
    (if (pos? delta)
      (.setTurnRadarLeftRadians this delta)
      (.setTurnRadarRightRadians this delta))))

(defn- execute! [^AdvancedRobot this]
  (.execute this))

(defn- adjust-gun-for-robot-turn! [^AdvancedRobot this adjust?]
  (.setAdjustGunForRobotTurn this adjust?))

(defn- update-gun-state [^AdvancedRobot this robot]
  (-> robot
      (assoc-in [:gun :heading] (.getGunHeadingRadians this))
      (update-in [:gun :targeted-heading] #(when (not (zero? (.getGunTurnRemainingRadians this))) %))))

(defn- update-radar-state [^AdvancedRobot this robot]
  (-> robot
      (assoc-in [:radar :heading] (.getRadarHeadingRadians this))
      (update-in [:radar :targeted-heading] #(when (not (zero? (.getRadarTurnRemainingRadians this))) %))))

(defn- update-state [^AdvancedRobot this robot]
  (->> robot
       (update-gun-state this)
       (update-radar-state this)))

(defn run [^AdvancedRobot this onLoop]

  (adjust-gun-for-robot-turn! this (get-in @this [:gun :adjust-for-robot-turn?]))

  (loop [robot @this]

    (let [actual-state (update-state this robot)
          targeted-state (onLoop actual-state)
          [_ changed _] (diff actual-state targeted-state)]

      (when-let [targeted-heading (get-in changed [:gun :targeted-heading])]
        (turn-gun! this targeted-heading))

      (when-let [targeted-heading (get-in changed [:radar :targeted-heading])]
        (turn-radar! this targeted-heading))

      (execute! this)

      (swap! (.robot this) #(merge-with merge % actual-state changed)))

    (recur @this)))