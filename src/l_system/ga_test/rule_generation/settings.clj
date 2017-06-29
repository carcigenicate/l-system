(ns l-system.ga-test.rule-generation.settings
  (:require [ai-retry.genetic-algorithm.settings :as s]
            [ai-retry.genetic-algorithm.population :as p]

            [l-system.ga-test.rule-generation.fitness-f :as f]

            [helpers.general-helpers :as g]))

; TODO: Add save/restore State!
(def dummy ::dummy)
(def alphabet #{::f ::b :l ::r ::rule-end dummy})

(def pop-size 100)
(def mutation-rate 0.05)
(def elite-perc 0.4)
(def less-perc 0.4)
(def keep-perc 0.4)

(def sequence-length 50)

(def fit-err-comp p/std-fitness-comparator)

(def problem-settings
  (s/->Problem-Settings f/new-fitness-f alphabet fit-err-comp))

(def standard-settings
  (s/->Standard-Settings elite-perc less-perc keep-perc mutation-rate pop-size))

(def rule-generation-settings
  (s/->Settings standard-settings problem-settings))

(defn starting-pop [rand-gen]
  (p/new-random-population alphabet sequence-length pop-size rand-gen))