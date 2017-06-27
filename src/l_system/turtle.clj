(ns l-system.turtle
  (:require [quil.core :as q]))

(defn move-forward [by]
  (q/line 0 0 0 (- by))
  (q/translate 0 (- by)))

(defn move-backward [by]
  (q/translate 0 by))

(defn turn-left [by]
  (q/rotate (- by)))

(defn turn-right [by]
  (q/rotate by))

(defn save-state []
  (q/push-matrix))

(defn restore-state []
  (q/pop-matrix))