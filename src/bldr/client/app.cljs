(ns bldr.client.app
  (:require
    [biff.client :as bc]
    [reitit.frontend :as rf]
    [reitit.frontend.easy :as rfe]
    [rum.core :as rum]
    [bldr.client.app.components :as c]
    [bldr.client.app.db :as db]
    [bldr.client.app.mutations :as m]
    [bldr.client.app.system :as s]))

(defn ^:export mount []
  (rum/mount (c/main) (js/document.querySelector "#app")))

(defn ^:export init []
  (reset! s/system
    (bc/init-sub {:handler m/api
                  :sub-results db/sub-results
                  :subscriptions db/subscriptions}))
  (rfe/start!
    (rf/router c/routes)
    #(reset! db/route %)
    {:use-fragment false})
  (mount))
