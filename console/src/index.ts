import { definePlugin, BasicLayout } from "@halo-dev/console-shared";
import UmamiView from "./views/UmamiView.vue";
import MaterialSymbolsAnalyticsOutline from "~icons/material-symbols/analytics-outline";
import { markRaw } from "vue";

export default definePlugin({
  name: "PluginUmami",
  components: [],
  routes: [
    {
      path: "/umami",
      component: BasicLayout,
      children: [
        {
          path: "",
          name: "Umami",
          component: UmamiView,
        },
      ],
    },
  ],
  menus: [
    {
      name: "统计",
      items: [
        {
          name: "Umami",
          path: "/umami",
          icon: markRaw(MaterialSymbolsAnalyticsOutline),
        },
      ],
    },
  ],
  extensionPoints: {},
});
