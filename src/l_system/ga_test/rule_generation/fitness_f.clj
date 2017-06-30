(ns l-system.ga-test.rule-generation.fitness-f
  (:require [l-system.ga-test.rule-generation.genetic-l-system :as gls]
            [helpers.general-helpers :as g]
            [l-system.ga-test.rule-generation.mutable-states :as ms]))

; TODO: Appropriate place?
(def n-expansions 6)
(def min-allowed-sentence-length 20)

(def min-score 0)

(def listener-loop-delay 333)

(defn fitness-f [genes]
  (let [l-system? (gls/parse-genes genes)]

    (if l-system?
      (let [expanded (gls/expand-l-system l-system? n-expansions)]

        (if (> (count expanded) min-allowed-sentence-length)
          (do
            ; Show l-system
            (dosync
              (ref-set ms/!current-l-system! l-system?))

            (while (nil? @ms/!last-rating!)
              (Thread/sleep listener-loop-delay))

            (let [rating (ms/read-and-clear ms/!last-rating!)]
              rating))

          min-score))

      min-score)))
