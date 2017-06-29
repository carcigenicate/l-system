(ns l-system.ga-test.rule-generation.process-loop
  (:require [l-system.ga-test.rule-generation.settings :as se]
            [ai-retry.genetic-algorithm.population :as p]
            [helpers.general-helpers :as g]))

(def !running?! (atom true))

; TODO: Terrible name
(defn process-loop []
  (let [rand-gen     (g/new-rand-gen 99)
        settings     se/rule-generation-settings
        {{ps :pop-size} :standard {pgt :possible-gene-types} :problem} settings
        starting-pop (p/new-random-population pgt se/sequence-length ps rand-gen)]

    (Thread. ^Runnable
             (fn []

               (loop [acc-pop starting-pop]
                 (when @!running?!
                   (let [ap (p/advance-population pop settings se/fit-err-comp rand-gen)]
                     (recur ap))))))))

(.addShutdownHook
  (Runtime/getRuntime)
  (Thread. ^Runnable
           (reset! !running?! false)))