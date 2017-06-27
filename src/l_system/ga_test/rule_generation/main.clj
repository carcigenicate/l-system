(ns l-system.ga-test.rule-generation.main
  (:require [quil.core :as q]
            [quil.middleware :as m]

            [l-system.ga-test.rule-generation.settings :as ls]
            [helpers.general-helpers :as g]))

(def width 1000)
(def height 1000)

; TODO: EWW!
(def !last-rating! (atom nil))
(def !advanced-population! (atom nil))

(def global-rand-gen (g/new-rand-gen 99))

(def settings ls/rule-generation-settings)

(defrecord State [population])

(defn setup-state [])


(defn update-state [state]
  (let [{lr :last-rating } state]))

(defn draw-state [state])

(defn mouse-click-handler [state {x :x}]
  (reset! !last-rating! x))


(defn -main []
  (q/defsketch GA-L-System-Rule-Gen-Test
               :size [width height]

               :setup setup-state
               :update update-state
               :draw draw-state

               :middleware [m/fun-mode]

               :mouse-clicked mouse-click-handler))
