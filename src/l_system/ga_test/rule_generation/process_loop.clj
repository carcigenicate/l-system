(ns l-system.ga-test.rule-generation.process-loop
  (:require [l-system.ga-test.rule-generation.settings :as se]
            [l-system.ga-test.rule-generation.fitness-f :as f]
            [ai-retry.genetic-algorithm.population :as p]
            [helpers.general-helpers :as g]))

(def !running?! (atom true))

(def settings
  (assoc-in se/rule-generation-settings [:problem :fitness-f]
            f/fitness-f))

; TODO: Terrible name
(defn process-loop []
  (let [rand-gen     (g/new-rand-gen 99)
        {{ps :pop-size} :standard {pgt :possible-gene-types} :problem} settings
        starting-pop (p/new-random-population pgt se/sequence-length ps rand-gen)]

    (doto
      (Thread. ^Runnable
               (fn []

                 (loop [acc-pop starting-pop]
                   (when @!running?!
                     (let [ap (p/advance-population acc-pop settings se/fit-err-comp rand-gen)]
                       (println "Recursed...")
                       (recur ap))))

                 (println "!!! Thread Ended !!!")))
      (.start))))

(.addShutdownHook
  (Runtime/getRuntime)
  (Thread. ^Runnable
           (fn []
             (println "Setting running to false...")
             (reset! !running?! false))))