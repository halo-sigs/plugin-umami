<script lang="ts" setup>
import { onMounted } from "vue";
import apiClient from "@/utils/api-client";
import type { ConfigMap } from "../types";
import { Dialog } from "@halo-dev/components";
import { ref } from "vue";
import { useRouter } from "vue-router";

const shareUrl = ref("");

const router = useRouter();

const handleFetchUmamiShareUrl = async () => {
  try {
    const { data: configMap } = await apiClient.get<ConfigMap>(
      "/api/v1alpha1/configmaps/plugin-umami-configMap"
    );

    shareUrl.value = JSON.parse(configMap.data?.basic || "{ url: '' }").url;
  } catch (error) {
    Dialog.warning({
      title: "未正确配置 Umami 的共享链接",
      description:
        "当前没有正确配置 Umami 的共享链接，可以点击下方按钮进入设置。",
      confirmText: "进入设置",
      onConfirm: () => {
        router.push(`/plugins/PluginUmami/settings/basic`);
      },
    });
    console.error(error);
  }
};

onMounted(handleFetchUmamiShareUrl);
</script>
<template>
  <iframe :src="shareUrl" />
</template>
<style scoped>
iframe {
  width: 100%;
  height: 100vh;
  border: none;
}
</style>
