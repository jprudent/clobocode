(ns jprudent.clobocode.math)

(def ^:const PI Math/PI)
(def ^:const PIPI (* 2 PI))
(def ^:const PI÷2 (/ PI 2))

(defn normalize-angle
  "Given an angle in radian return a positive value between 0 and 2PI"
  [a]
  (cond
    (neg? a) (normalize-angle (+ a PIPI))
    (> a PIPI) (normalize-angle (- a PIPI))
    :else a))