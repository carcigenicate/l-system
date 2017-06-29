(ns l-system.ga-test.rule-generation.main
  (:require [quil.core :as q]
            [quil.middleware :as m]

            [l-system.ga-test.rule-generation.settings :as se]
            [l-system.ga-test.rule-generation.fitness-f :as gaf]
            [l-system.ga-test.rule-generation.genetic-l-system :as gls]

            [helpers.general-helpers :as g]
            [helpers.quil-helpers :as qh]))

(def width 1500)
(def height 1500)

; TODO: The axiom needs to allow multiple symbols
; TODO: Multiple symbols for each action?
; TODO: Add [ and ]

; TODO: EWW!
; TODO: Reset both after reading
(def !last-rating! (atom nil))
(def !current-l-system! (atom nil))

(def n-expansions 15)

(def global-rand-gen (g/new-rand-gen 99))

(defrecord State [population])

(defn setup-state []
  (q/frame-rate 1)
  (reset! !current-l-system!
          (gls/->L-System [::se/f]
                          {::se/f, [::se/f ::se/l ::se/f ::se/r ::se/f ::se/r ::se/f ::se/l ::se/f]}))
  nil)


(defn update-state [_]
  (let [cls @!current-l-system!]
       (if cls
         (let [sentence (gls/expand-l-system cls n-expansions)]
           sentence))))

(defn draw-state [sentence]
  (when sentence
    #_
    (q/background 200 200 200)

    (q/with-translation [(* width 0.7) (* height 0.9)]
      (qh/with-weight 2
        (gls/draw-l-system-sentence sentence)))

    (println "Drawn!")))

; TODO: Reset !last-rating! back to nil after reading
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
