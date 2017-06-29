(ns l-system.l-system)

(defn expand [sym rules]
  (reduce (fn [acc [pre suc]]
            (if (= sym pre)
              (reduced suc)
              acc))
          sym
          rules))

(defn next-sentence [sentence rules]
  (reduce (fn [acc sym]
            (into acc (rules sym [sym])))
          []
          sentence))

(defn nth-sentence [sentence rules n]
  (last
    (take n
      (iterate #(next-sentence % rules) sentence))))

(def basic-rules
  {\A "AB"
   \B "A"})

(def complex-rules
  {\A "ABC"
   \B "AC"
   \C "BC"})

(def sierpinski-rules
  {\F "F-G+F+G-F"
   \G "GG"})

(def koch-rules
  {\F "F+F-F-F+F"})

(def tree-rules
  {\X "F-[[X]+X]+F[+FX]-X"
   \F "FF"})

(def test-tree-rules
  {\X "[F-[[X]+X]+]F[+FX]-X"
   \F "FF"})