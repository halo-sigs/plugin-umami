<script lang="ts" setup>
import { onMounted } from "vue";
import apiClient from "@/utils/api-client";
import type { ConfigMap } from "../types";
import { ref } from "vue";

const shareUrl = ref("");

const handleFetchUmamiShareUrl = async () => {
  try {
    const { data: configMap } = await apiClient.get<ConfigMap>(
      "/api/v1alpha1/configmaps/plugin-umami-configMap"
    );

    shareUrl.value = JSON.parse(configMap.data?.basic || "{ url: '' }").url;
  } catch (error) {
    alert("未正确配置 Umami 的共享链接");
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
