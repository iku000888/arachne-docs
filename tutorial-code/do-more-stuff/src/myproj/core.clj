(ns myproj.core
  (:require [arachne.log :as log]
            [com.stuartsierra.component :as c]
            [hiccup2.core :as h]
            [ring.util.response :as res]))

(defrecord Widget []
  c/Lifecycle
  (start [this]
    (log/info :msg "Hello, world!")
    this)
  (stop [this]
    (log/info :msg "Goodnight!")
    this))

(defn make-widget
  "Constructor for a Widget"
  []
  (->Widget))

(defn hello-handler
  [req]
  {:status 200
   :body "Hello, world!"})

(defn greeter
  [req]
  (let [name (get-in req [:path-params :name])]
    {:status 200
     :body (str "Hello, " name "!")}))

;; New code
(defn fun-handler [req]
  (-> {:status 200
       :body
       (-> [:div "Let's have fun!"
            [:hr]
            #_[:form {:method "POST"}
               [:input {:name "foo"}]
               [:input {:type "submit"
                        :value "send!!!"}]]
            ;;[:hr] "url for fun = " ((:url-for req) :myproj/fun )
            ;;[:hr] "access count= " (pr-str (:history-store req))
            ]
           h/html
           str)}
      (res/content-type  "text/html; charset=utf-8")))

(defn num-atom []
  (atom 0))

(defn post-handler [req]
  ;;(swap! (:history-store req) inc)
  {:status 200
   :body (pr-str req)})

(comment
  (require '[arachne.core :as arachne])
  (require '[com.stuartsierra.component :as c])

  (def cfg (arachne/config :myproj/app))
  (def rt (arachne/runtime cfg :myproj/runtime))
  (def rt (c/start rt))
  (def rt (c/stop rt))

  ;;Force system resart
  (do (c/stop rt)
      (def cfg (arachne/config :myproj/app))
      (def rt (arachne/runtime cfg :myproj/runtime))
      (def rt (c/start rt)))

  (require '[arachne.core.config :as cfg])
  (cfg/q cfg '[:find ?e
               :where
               [?e :arachne.http.endpoint/route _]
               ])

  (cfg/pull cfg '[*] 43)

  )
