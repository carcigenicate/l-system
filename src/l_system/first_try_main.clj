(ns l-system.first-try-main
  (:require [helpers.general-helpers :as g]
            [helpers.quil-helpers :as qh]

            [l-system.l-system :as l]
            [l-system.turtle :as t]

            [quil.core :as q])
  (:gen-class))

(def width 2500)
(def height 1500)

(def rotate-amount (q/radians 15))
(def move-amount 10)

(def action-dispatch
  {\F #(t/move-forward move-amount)
   \G #(t/move-forward move-amount)
   \+ #(t/turn-left rotate-amount)
   \- #(t/turn-right rotate-amount)
   \[ t/save-state
   \] t/restore-state})

(defn setup-state []
  (let [axiom "X"
        rules l/test-tree-rules
        n-iterations 7
        sentence (l/nth-sentence axiom rules n-iterations)

        actions (map action-dispatch sentence)]

    (q/with-translation [(* width 0.5) (* height 1)]
      (qh/with-weight 2
        (doseq [action actions
                :let [checked-action (or action #())]]
          (checked-action))))

    (println "Drew")))

(defn -main []
  (q/defsketch L-System
               :size [width height]
               :setup setup-state))

