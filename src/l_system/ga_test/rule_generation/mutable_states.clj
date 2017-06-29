(ns l-system.ga-test.rule-generation.mutable-states)

(defn read-and-clear [r]
  (dosync
    (let [contents @r]
      (ref-set r nil)
      contents)))

; TODO: EWW!
(def !last-rating! (ref nil))
(def !current-l-system! (atom nil))