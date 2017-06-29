(ns l-system.ga-test.rule-generation.genetic-l-system
  (:require [l-system.ga-test.rule-generation.settings :as se]
            [l-system.l-system :as ls]
            [l-system.turtle :as t]
            [quil.core :as q]))

(defrecord L-System [axiom rules])

(def move-amount 1)
(def rotate-amount 1.5707964)

(def rule-end-marker ::se/rule-end)

; TODO: Add save/restore State!
(def command-dispatch
  {::se/f #(t/move-forward move-amount)
   ::se/g #(t/move-forward move-amount)
   ::se/b #(t/move-backward move-amount)

   ::se/l #(t/turn-left rotate-amount)
   ::se/r #(t/turn-right rotate-amount)})

(defn remove-dummies [genes]
  (remove #{se/dummy} genes))

(defn valid-rule? [rule]
  (>= (count rule) 3))

(defn pop-first-rule-string
  "Returns a pair of [rule rest-rule-string]
  Doesn't check if the rule is actually valid."
  [rule-string]
  (let [[rule rest-rule-string] (split-with #(not= rule-end-marker %) rule-string)]
    [rule rest-rule-string]))

(defn parse-rules [rule-string]
  (loop [remaining-rule-string rule-string
         acc {}]

    (if (not (empty? remaining-rule-string))
      (let [[rule rest-rule-string] (pop-first-rule-string remaining-rule-string)]
        (println rule "-" rest-rule-string)
        (if (valid-rule? rule)
          (let [[source & result] rule
                rest-rule-string' (drop 1 rest-rule-string)] ; Dropping the marker
            (recur rest-rule-string'
                   (assoc acc source result)))
          acc))
      acc)))

(defn parse-genes
  "Returns an L-System object if the genes can be properly parsed as an L-System.
  Returns nil otherwise."
  [genes]
  (let [dummies-removed-genes (remove-dummies genes)]

    (when (>= (count dummies-removed-genes) 4)
      (let [[axiom & rule-string] dummies-removed-genes
            parsed-rules (parse-rules rule-string)]

        (if-not (empty? parsed-rules)
          (->L-System axiom parsed-rules)
          nil)))))

(defn expand-l-system [l-system n-expansions]
  (let [{axiom :axiom, rules :rules} l-system]
    (ls/nth-sentence axiom rules n-expansions)))

(defn expand-l-system-genes
  "Takes a gene sequence, parses them as an L-System, and expands the system.
  Returns nil if there's a problem parsing the genes."
  [genes n-expansions]
  (when-let [l-system (parse-genes genes)]
    (expand-l-system l-system n-expansions)))

(defn draw-l-system-sentence [sentence]
  (let [commands (map #(get command-dispatch % (fn [])) sentence)]
    (doseq [c commands]
      (c))))


