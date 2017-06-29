(ns l-system.ga-test.rule-generation.fitness-f
  (:require [l-system.ga-test.rule-generation.genetic-l-system :as gls]
            [helpers.general-helpers :as g]))

(def listener-loop-delay 333)

(defn new-fitness-f [input-atom l-system-atom]
  (fn [genes]
    (let [l-system? (gls/parse-genes genes)]
      (if l-system?
        (do
          ; Show l-system
          (reset! l-system-atom l-system?)

          (while (nil? @input-atom)
            (Thread/sleep listener-loop-delay))

          @input-atom)

        0))))


#_
(.addShutdownHook
  (Runtime/getRuntime)
  (Thread. ^Runnable
           (constantly)))