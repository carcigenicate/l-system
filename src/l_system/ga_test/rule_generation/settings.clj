(ns l-system.ga-test.rule-generation.settings
  (:require [ai-retry.genetic-algorithm.settings :as s]
            [ai-retry.genetic-algorithm.population :as p]

            [helpers.general-helpers :as g]))

; TODO: Add save/restore State!
(def dummy ::dummy)
(def alphabet #{::F ::f
                ::L ::l
                ::R ::r
                ::save ::load

                ::rule-end
                dummy})

(def pop-size 20)
(def mutation-rate 0.05)
(def elite-perc 0.4)
(def less-perc 0.4)
(def keep-perc 0.5)

(def sequence-length 50)

(def fit-err-comp p/std-fitness-comparator)

(def problem-settings
  "Problem settings with a nil fitness-f. Needs to be set before it can be used."
  (s/->Problem-Settings nil alphabet fit-err-comp))

(def standard-settings
  (s/->Standard-Settings elite-perc less-perc keep-perc mutation-rate pop-size))

(def rule-generation-settings
  (s/->Settings standard-settings problem-settings))

(defn starting-pop [rand-gen]
  (p/new-random-population alphabet sequence-length pop-size rand-gen))