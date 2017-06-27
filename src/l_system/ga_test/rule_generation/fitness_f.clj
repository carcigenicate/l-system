(ns l-system.ga-test.rule-generation.fitness-f)

(def listener-loop-delay 333)

(defn draw-l-system [])

(defn new-fitness-f [input-atom]
  (fn [genes]
    ()
    (while (nil? @input-atom)
      (Thread/sleep listener-loop-delay))

    @input-atom))

(.addShutdownHook
  (Runtime/getRuntime)
  (Thread. ^Runnable
           (constantly)))