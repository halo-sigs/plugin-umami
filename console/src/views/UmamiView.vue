<script lang="ts" setup>
import { coreApiClient } from "@halo-dev/api-client";
import { Toast } from "@halo-dev/components";
import { onMounted, ref } from "vue";

const shareUrl = ref("");
const pluginDetailModal = ref(false);

const handleFetchUmamiShareUrl = async () => {
  try {
    const { data: configMap } = await coreApiClient.configMap.getConfigMap({
      name: "plugin-umami-configMap",
    });

    const url = JSON.parse(configMap.data?.basic || "{ url: '' }").url;

    if (!url) {
      throw new Error("Umami share url is empty");
    }

    shareUrl.value = url;
  } catch (error) {
    Toast.success("未正确配置 Umami 共享链接，请先配置");
    pluginDetailModal.value = true;
  }
};

onMounted(handleFetchUmamiShareUrl);

function onPluginDetailModalClose() {
  pluginDetailModal.value = false;
  handleFetchUmamiShareUrl();
}
</script>
<template>
  <PluginDetailModal
    v-if="pluginDetailModal"
    name="PluginUmami"
    @close="onPluginDetailModalClose"
  />
  <iframe :src="shareUrl" style="width: 100%; height: 100vh; border: none" />
</template>
