(ns l-system.ga-test.rule-generation.fitness-f
  (:require [l-system.ga-test.rule-generation.genetic-l-system :as gls]
            [helpers.general-helpers :as g]
            [l-system.ga-test.rule-generation.mutable-states :as ms]))


(def listener-loop-delay 333)

(defn fitness-f [genes]
  (let [l-system? (gls/parse-genes genes)]

    (if l-system?
      (do
        ; Show l-system
        (dosync
          (ref-set ms/!current-l-system! l-system?))

        (while (nil? @ms/!last-rating!)
          (Thread/sleep listener-loop-delay))

        (let [rating (ms/read-and-clear ms/!last-rating!)]
          rating))

      0)))
