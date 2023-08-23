import { definePlugin } from "@halo-dev/console-shared";
import UmamiView from "./views/UmamiView.vue";
import MaterialSymbolsAnalyticsOutline from "~icons/material-symbols/analytics-outline";
import { markRaw } from "vue";

export default definePlugin({
  routes: [
    {
      parentName: "Root",
      route: {
        path: "/umami",
        children: [
          {
            path: "",
            name: "Umami",
            component: UmamiView,
            meta: {
              title: "Umami",
              searchable: true,
              menu: {
                name: "Umami",
                group: "tool",
                icon: markRaw(MaterialSymbolsAnalyticsOutline),
                priority: 0,
              },
            },
          },
        ],
      },
    },
  ],
  extensionPoints: {},
});
