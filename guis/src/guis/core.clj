(ns guis.core
  (:use seesaw.core)
  (:use seesaw.font)
  (:gen-class))

(native!)

(def mainFrame (frame :title "Clojure Docs", :content "Hello, Seesaw",
:on-close :exit))

(defn -main
  "Show a frame with Hello World text on window."
  [& args]
  (invoke-later
    (-> mainFrame
     pack!
     show!)))

; Function to add elements to mainFrame
(defn display [content]
 (config! mainFrame :content content)
 content)

(def docsListBox (listbox :model (-> 'seesaw.core ns-publics keys sort)))

(selection docsListBox {:multi? false})

; (display (scrollable docsListBox))

(def documentationText (text :multi-line? true :font "MONOSPACED-PLAIN-14"))

(def splitView
  (left-right-split
    (scrollable docsListBox)
    (scrollable documentationText)
    :divider-location 1/3))

;(display splitView)

; Let's hook them together. First a function to grab a doc string
(defn doc-str [s] (-> (symbol "seesaw.core" (name s)) resolve meta :doc))

(listen docsListBox :selection
        (fn [e]
          (when-let [s (selection e)]
            (-> documentationText
              (text!   (doc-str s))
              (scroll! :to :top)))))

(def SourceOrDocsRadioButtons (for [i [:source :doc]]
           (radio :id i :class :type :text (name i))))

(display (border-panel
          :north (horizontal-panel :items SourceOrDocsRadioButtons)
          :center splitView
          :vgap 5 :hgap 5 :border 5))

(def radioBtnGroup (button-group))

(config! (select mainFrame [:.type]) :group radioBtnGroup)




(comment "

; macros:

  (defmacro backwards
    [form]
    (reverse form))
  ;
  (backwards (" backwards" " am" " I" str))


  (def addition-list (list + 1 2))
  (eval addition-list)
; => 3

  (read-string "(+ 1 2)")
; => (+ 1 2)

; special forms:

; if, def, let, loop, fn, do, quote, and recur

  (defmacro infix
    [infixed]
    (list (second infixed)
          (first infixed)
          (last infixed)))
;
(infix (1 + 2))
; => 3

(defmacro code-critic
  [{:keys [good bad]}]
  `(do ~@(map #(apply criticize-code %)
              [["Sweet lion of Zion, this is bad code:" bad]
               ["Great cow of Moscow, this is good code:" good]])))

")
