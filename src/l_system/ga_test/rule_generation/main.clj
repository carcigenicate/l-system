(ns l-system.ga-test.rule-generation.main
  (:require [quil.core :as q]
            [quil.middleware :as m]

            [l-system.ga-test.rule-generation.settings :as se]
            [l-system.ga-test.rule-generation.fitness-f :as gaf]
            [l-system.ga-test.rule-generation.genetic-l-system :as gls]

            [helpers.general-helpers :as g]))

(def width 1000)
(def height 1000)

; TODO: EWW!
; TODO: Reset both after reading
(def !last-rating! (atom nil))
(def !current-l-system! (atom nil))

(def n-expansions 3)

(def global-rand-gen (g/new-rand-gen 99))

(def settings se/rule-generation-settings)

(defrecord State [population])

(defn setup-state []
  (reset! !current-l-system! (gls/->L-System ::se/f {::se/f [::l ::se/dummy ::se/r ::se/f ::se/r ::se/dummy ::se/l ::se/f ::se/rule-end]
                                                     ::se/dummy [::se/dummy ::se/dummy]})))


(defn update-state [_]
  (let [cls @!current-l-system!]
    (if cls
      (let [sentence (gls/expand-l-system cls n-expansions)]
        ; TODO: sentence begins with 2 Map Entries? <---------------------------
        sentence))))


(defn draw-state [sentence]
  (when sentence
    (clojure.pprint/pprint (map type sentence))
    (q/background 200 200 200)

    (gls/draw-l-system-sentence sentence)))

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
