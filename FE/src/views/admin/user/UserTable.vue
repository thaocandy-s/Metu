<template>
  <div class="shadow-xl p-3 m-3 rounded-md flex h-full flex-col overflow-auto">
    <div class="flex justify-between items-center min-h-36">
      <h2 class="p-4 flex items-center text-primary text-3xl font-semibold">
        <v-icon name="bi-list-ul" class="text-black" scale="2" />
        <span class="ml-2 text-2xl text-black">Danh sách người dùng</span>
      </h2>
    </div>
    <div class="flex h-0 flex-1 flex-col">
      <MetuTable 
        wrapperClassName="min-h-[410px]" 
        :columns="columnsSubject" 
        :data-source="dataSource" 
        :loading="loading"
        :pagination-params="paginationParams || {}" 
        :total-pages="totalPages || 0"
        @update:pagination-params="$emit('update:paginationParams', $event)">
        
        <template #bodyCell="{ column, record }">
          <div v-if="column.key === 'action'" class="space-x-2 flex items-center justify-center">
            <a-tooltip title="Chỉnh sửa người dùng" color="#1E90FF">
              <a-button class="flex items-center justify-center" type="primary" size="large"
                @click="$emit('handleOpenModalUpdate', record)" :icon="h(EditOutlined)" />
            </a-tooltip>
            <a-tooltip title="Cập nhật trạng thái người dùng" color="#1E90FF">
              <a-button class="flex items-center justify-center" type="primary" size="large"
                @click="showConfirmChangeStatusUser(record.id)" :icon="h(DeleteOutlined)" />
            </a-tooltip>
          </div>

          <div v-else-if="column.key === 'avatarImage'" class="avatar-cell">
            <img :src="record.avatarImage" class="avatar-image" style="width: 50px; height: 50px; border-radius: 50%;" />
          </div>

          <div v-else-if="column.key === 'rating'">
            <a-rate v-model:value="record.rating" disabled allow-half />
          </div>

          <div v-else-if="column.key === 'status'">
            <a-tag :color="record.status === 0 ? 'green' : 'red'">
              {{ record.status === 0 ? "Đang hoạt động" : "Ngừng hoạt động" }}
            </a-tag>
          </div>

          <div v-else>
            {{ record[column.dataIndex] }}
          </div>
        </template>
      </MetuTable>
    </div>

    <a-modal v-model:open="confirmchangeStatusUser" title="Xác nhận thay đổi trạng thái" @cancel="confirmchangeStatusUser = false"
      @ok="handlechangeStatusUser">
      <p>Bạn có chắc chắn muốn thay đổi trạng thái không?</p>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import MetuTable from "@/components/forms/MetuTable.vue";
import { UserResponse } from "@/services/api/user/user.api";
import { EditOutlined, DeleteOutlined } from "@ant-design/icons-vue";
import { h, ref } from "vue";

defineProps({
  dataSource: Array<UserResponse>,
  loading: Boolean,
  paginationParams: Object,
  totalPages: Number,
});

const emit = defineEmits([
  "update:paginationParams",
  "handleOpenModalUpdate",
  "handleConfirmChangeStastus"
]);

const confirmchangeStatusUser = ref(false);
const idTochangeStatus = ref<string | null>(null);

const showConfirmChangeStatusUser = (id: string) => {
  confirmchangeStatusUser.value = true;
  idTochangeStatus.value = id;
};

const handlechangeStatusUser = () => {
  if (idTochangeStatus.value) {
    emit('handleConfirmChangeStastus', idTochangeStatus.value);
  }
  confirmchangeStatusUser.value = false;
  idTochangeStatus.value = null;
};

const columnsSubject = [
  { title: "STT", dataIndex: "stt", key: "stt", ellipsis: true, width: 50 },
  { title: "Avatar", dataIndex: "avatarImage", key: "avatarImage", width: 80 },
  { title: "Mã người dùng", dataIndex: "code", key: "code" },
  { title: "Tên người dùng", dataIndex: "name", key: "name" },
  { title: "Tên doanh nghiệp", dataIndex: "nameBusiness", key: "nameBusiness" },
  { title: "Email", dataIndex: "email", key: "email" },
  { title: "Vai trò", dataIndex: "nameRole", key: "nameRole", width: 80 },
  { title: "Địa chỉ", dataIndex: "address", key: "address", width: 80 },
  { title: "Đang trực tiếp", dataIndex: "isOnline", key: "isOnline", width: 100 },
  { title: "Lần cuối hoạt động", dataIndex: "LastTimeActive", key: "LastTimeActive", width: 100 },
  { title: "Đánh giá", dataIndex: "rating", key: "rating", width: 180 },
  { title: "Trạng thái", dataIndex: "status", key: "status", width: 80 },
  { title: "Hành Động", align: "center", width: "200px", key: "action" },
];
</script>
