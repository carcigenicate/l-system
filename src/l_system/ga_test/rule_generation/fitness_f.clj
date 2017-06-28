(ns l-system.ga-test.rule-generation.fitness-f
  (:require [l-system.ga-test.rule-generation.genetic-l-system :as gls]))

(def listener-loop-delay 333)

; TODO: Need to run genetic algorithm code on a different thread because of async fitness-f
; Start GA thread
; Busy wait for input
; Parse genes
; Output

(defn draw-l-system [])

(defn new-fitness-f [input-atom display-atom]
  (fn [genes]
    (let [l-system? (gls/parse-genes genes)]
      (if l-system?
        (do
          ; Show l-system
          (reset! display-atom l-system?)

          (while (nil? @input-atom)
            (Thread/sleep listener-loop-delay))

          @input-atom)

        0))))

; TODO: Terrible name
(defn process-loop [input-atom display-atom]
  (Thread. ^Runnable
           (fn []
             (while true))))


#_
(.addShutdownHook
  (Runtime/getRuntime)
  (Thread. ^Runnable
           (constantly)))