(ns bldr.client.app.components
  (:require
    [clojure.pprint :as pp]
    [reitit.frontend.easy :as rfe]
    [rum.core :as rum :refer [react defc defcs reactive local]]
    [bldr.client.app.db :as db]
    [bldr.client.app.mutations :as m]
;;    [images/add_components.svg :as addComponentIcon]
    ))

; See https://github.com/tonsky/rum

(def routes
  [["/app/" {:name :crud}]
   ["/app/db" {:name :db}]])


(defc db-contents < reactive
  []
  [:div
   [:pre.text-sm (with-out-str (pp/pprint (react db/data)))]
   [:hr.my-6]
   [:p "When you've had enough fun here, start reading through the code. Here are some good "
    "starting points:"]
   [:ul.list-disc.pl-8.font-mono
    [:li "src/bldr/client/app.cljs"]
    [:li "src/bldr/core.clj"]
    [:li "all-tasks/10-biff"]
    [:li "config/"]]])

(defcs set-value < reactive (local "" ::tmp-value)
  [{::keys [tmp-value]} {:keys [label model mutate description]}]
  [:div
   [:.text-lg label ": " [:span.font-mono (pr-str (react model))]]
   [:.text-sm.text-gray-600 description]
   [:.h-1]
   [:.flex
    [:button.btn {:on-click #(mutate @tmp-value)}
     "+"]
    [:.w-2]
    [:button.btn {:on-click #(mutate @tmp-value)}
     "..."]
    [:.w-2]
    [:input.input-text.w-full
     {:value @tmp-value
      :on-change #(reset! tmp-value (.. % -target -value))}]
    [:.w-3]
    [:button.btn {:on-click #(mutate @tmp-value)}
     "*"]]])


(defc crud < reactive
  []
  [:div
   (set-value {:label "Component"
               :model db/foo
               :mutate m/set-foo
               :description "A simple label component for the home page."})])

(defc main < reactive
  []
  [:div
   [:.flex
;;    [:div "Signed in as " (react db/email)]
    [:.flex-grow]
    [:a.text-blue-500.hover:text-blue-800 {:href "/api/signout"}
     "Sign out"]]
   [:.h-6]
   [:.h-3]
   (case (react db/tab)
     :crud (crud)
     :db (db-contents))])
