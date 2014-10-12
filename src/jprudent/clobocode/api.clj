(ns jprudent.clobocode.api
  (:import (robocode AdvancedRobot Rules))
  (:require [clojure.data :refer [diff]]
            [clojure.pprint :refer [pprint]]
            [jprudent.clobocode.math :refer [PIPI normalize-angle]]))

;; every angles are in radian
;; unless specified, an angle is relative to the world, like a direction on a compass
;; 0 is plain North, PI/2 is East, PI is South, 2PI/2 is West

(defrecord Gun [adjust-for-robot-turn? heading targeted-heading])
(defrecord Radar [adjust-for-robot-turn? heading targeted-heading])
(defrecord Robot [^Gun gun ^Radar radar])

(defn robot-factory
  "Build a Robot.
  If adjust-for-robot-turn? is true then the gun/radar act like it's independent of the body. When the body turn, it adjust to make illusion it doesn't turn. See robocode.Robot.setAdjustGunForRobotTurn."
  [adjust-for-robot-turn?]
  (->Robot (->Gun adjust-for-robot-turn? nil nil)
           (->Radar adjust-for-robot-turn? nil nil)))

(defprotocol Pivotable
  "Anything that can turn"
  (turn-left-rel [this angle] "Turn left relatively to current position")
  (turn-right-rel [this angle] "Turn right relatively to current position")
  (head-to [this bearing] "Head to specified angle"))

(def turn-impl
  {:turn-left-rel  (fn [this angle] (assoc this :targeted-heading (normalize-angle (+ (:heading this) (- angle)))))
   :turn-right-rel (fn [this angle] (assoc this :targeted-heading (normalize-angle (+ (:heading this) angle))))
   :head-to        (fn [this bearing] (assoc this :targeted-heading (normalize-angle bearing)))
   })

(extend Gun Pivotable turn-impl)
(extend Radar Pivotable turn-impl)
