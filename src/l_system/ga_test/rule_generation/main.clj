(ns l-system.ga-test.rule-generation.main
  (:require [quil.core :as q]
            [quil.middleware :as m]

            [l-system.ga-test.rule-generation.settings :as se]
            [l-system.ga-test.rule-generation.fitness-f :as gaf]
            [l-system.ga-test.rule-generation.genetic-l-system :as gls]
            [l-system.ga-test.rule-generation.mutable-states :as ms]
            [l-system.ga-test.rule-generation.process-loop :as pl]

            [helpers.general-helpers :as g]
            [helpers.quil-helpers :as qh]))

(def width 1500)
(def height 1500)

; TODO: The axiom needs to allow multiple symbols
; TODO: Multiple symbols for each action?
; TODO: Add [ and ]

(def n-expansions 6)
(def min-allowed-sentence-length 20)

(def global-rand-gen (g/new-rand-gen))

(defn format-l-system [l-system]
  (let [{a :axiom rs :rules} l-system]
    (str (name a) " - "
         (into {}
           (for [[s t] rs]
             [(name s) (mapv name t)])))))

(defn rate-l-system [rating]
  (dosync
    (ref-set ms/!last-rating! rating)))

(defn setup-state []
  (q/frame-rate 30)

  (pl/process-loop)

  nil)

(defn update-state [sentence]
  (let [cls (ms/read-and-clear ms/!current-l-system!)]
       (if cls
         (let [sentence' (gls/expand-l-system cls n-expansions)]
           (if (>= (count sentence') min-allowed-sentence-length)
             (do
               (println "Good:" (format-l-system cls))
               sentence')

             (do
               (println "Failed! Count:" (count sentence'))
               (rate-l-system 0)
               nil)))

         sentence)))

(defn draw-state [sentence]
  (when sentence
    (q/background 200 200 200)

    (q/with-translation [(* width 0.5) (* height 0.5)]
      (qh/with-weight 2
        (try
          (gls/draw-l-system-sentence sentence)

          (catch RuntimeException e
            (println "Failed:" (.getMessage e) (mapv name sentence))
            (rate-l-system 0)))))))

; TODO: Reset !last-rating! back to nil after reading
(defn mouse-click-handler [state {x :x}]
  (rate-l-system x)
  state)

(defn -main []
  (q/defsketch GA-L-System-Rule-Gen-Test
               :size [width height]

               :setup setup-state
               :update update-state
               :draw draw-state

               :middleware [m/fun-mode]

               :mouse-clicked mouse-click-handler))
