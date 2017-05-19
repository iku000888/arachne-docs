(ns ^:config myproj.config
  (:require [arachne.core.dsl :as a]
            [arachne.http.dsl :as h]
            [arachne.pedestal.dsl :as p]))

(a/id :myproj/widget-1 (a/component 'myproj.core/make-widget))

(a/id :myproj/runtime (a/runtime [:myproj/server :myproj/widget-1]))

(a/id :myproj/hello (h/handler 'myproj.core/hello-handler))

(a/id :myproj/num-atom
      (a/component 'myproj.core/num-atom))

(a/id :myproj/fun
      (h/handler 'myproj.core/fun-handler
                 #_{:history-store :myproj/num-atom}))

(a/id :myproj/fun-post
      (h/handler 'myproj.core/post-handler
                 #_{:history-store :myproj/num-atom}))

(a/id :myproj/server
      (p/server 8080

                (h/endpoint :get "/" :myproj/hello)
                (h/endpoint :get "/greet/:name" (h/handler 'myproj.core/greeter))

                ;; New handler
                ;;(h/endpoint :get "/fun" :myproj/fun)
                ;;(h/endpoint :post "/fun" :myproj/fun-post)

                ))
