<template>
    <div class="shadow-xl p-3 m-3 rounded-md flex h-full flex-col overflow-auto">
      <div class="flex justify-between items-center min-h-36">
        <h2 class="p-4 flex items-center text-primary text-3xl font-semibold">
          <v-icon name="bi-list-ul" class="text-black" scale="2" />
          <span class="ml-2 text-2xl text-black">Danh sách vị trí</span>
        </h2>
        <a-button type="primary" @click="$emit('handleOpenModalAdd')" size="large" class="m-4">
          Tạo vị trí
        </a-button>
      </div>
      <div class="flex h-0 flex-1 flex-col">
        <MetuTable wrapperClassName="min-h-[410px]" :columns="columnsPosition" :data-source="dataSource" :loading="loading"
          :pagination-params="paginationParams || {}" :total-pages="totalPages || 0"
          @update:pagination-params="$emit('update:paginationParams', $event)">
          <template #bodyCell="{ column, record }">
            <div v-if="column.key === 'action'" class="space-x-2 flex items-center justify-center">
              <a-tooltip title="Chỉnh sửa vị trí" color="#1E90FF">
                <a-button class="flex items-center justify-center" type="primary" size="large"
                  @click="$emit('handleOpenModalUpdate', record)" :icon="h(EditOutlined)" />
              </a-tooltip>
              <a-tooltip title="Thay đổi trạng thái vị trí" color="#1E90FF">
                <a-button
                  class="flex items-center justify-center"
                  type="primary"
                  size="large"
                  @click="showConfirmChangeStatus(record.positionId, record.positionStatus)" 
                  :icon="h(HeartOutlined)"
                />
              </a-tooltip>
            </div>
  
            <div v-else-if="column.key === 'positionIconUrl'">
              <img :src="record.positionIconUrl" alt="Icon" class="icon-image" />
            </div>
  
            <div v-else-if="column.key === 'positionStatus'">
              <a-tag v-if="record.positionStatus === 0" color="green" class="status-tag">Đang hoạt động</a-tag>
              <a-tag v-else color="red" class="status-tag">Ngừng hoạt động</a-tag>
            </div>
  
            <div v-else>
              {{ record[column.dataIndex] }}
            </div>
          </template>
        </MetuTable>
      </div>
  
      <a-modal v-model:open="confirmChangeStatusVisible" title="Xác nhận thay đổi trạng thái" @cancel="confirmChangeStatusVisible = false"
        @ok="handleChangeStatus">
        <p>Bạn có chắc chắn muốn thay đổi trạng thái vị trí này không?</p>
      </a-modal>
    </div>
  </template>
  
  <script setup lang="ts">
  import MetuTable from "@/components/forms/MetuTable.vue";
  import { PositionResponse } from "@/services/api/menu_style_config/position.api";
  import { EditOutlined, HeartOutlined } from "@ant-design/icons-vue";
  import { h, ref } from "vue";
  
  defineProps({
    dataSource: Array<PositionResponse>,
    loading: Boolean,
    paginationParams: Object,
    totalPages: Number,
  });
  
  const emit = defineEmits([
    "update:paginationParams",
    "handleOpenModalUpdate",
    "handleOpenModalAdd",
    "handleConfirmChangeStatus",
  ]);
  
  const confirmChangeStatusVisible = ref(false);
  const idToChangeStatus = ref<string | null>(null);
  const currentStatus = ref<number | null>(null);
  
  const showConfirmChangeStatus = (id: string, status: number) => {
    confirmChangeStatusVisible.value = true;  
    idToChangeStatus.value = id;
    currentStatus.value = status; 
  };
  
  const handleChangeStatus = () => {
    if (idToChangeStatus.value && currentStatus.value !== null) {
      const newStatus = currentStatus.value == 0 ? 1 : 0; 
      emit('handleConfirmChangeStatus', idToChangeStatus.value, newStatus); 
    }
    confirmChangeStatusVisible.value = false;
    idToChangeStatus.value = null;
    currentStatus.value = null; 
  };
  
  const columnsPosition = [
    { title: "STT", dataIndex: "stt", key: "stt", ellipsis: true },
    { title: "Mã", dataIndex: "positionCode", key: "positionCode" },
    { title: "Tên", dataIndex: "positionName", key: "positionName" },
    { title: "ClassName", dataIndex: "positionClassName", key: "positionClassName" },
    { title: "Icon_URL", dataIndex: "positionIconUrl", key: "positionIconUrl" },
    { title: "Trạng thái", dataIndex: "positionStatus", key: "positionStatus" },
    { title: "Mô Tả", dataIndex: "positionDescription", key: "positionDescription" },
    { title: "Hành động", dataIndex: "action", key: "action" },
  ];
  </script>
  
  <style scoped>
  .icon-image {
    width: 50px;
    height: 50px;
    object-fit: cover;
    border-radius: 8px;
  }
  
  .status-tag {
    display: inline-block;
    padding: 5px 10px;
    font-size: 14px;
    border-radius: 5px;
  }
  </style>
  