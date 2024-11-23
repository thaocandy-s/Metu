<template>
    <div class="mt-4">
      <div class="flex justify-between items-center">
        <h2 class="p-4 flex items-center text-primary text-3xl font-semibold">
          <v-icon name="hi-office-building" class="text-black" scale="2" />
          <span class="m-2 text-3xl text-black">Quản lý thể loại doanh nghiệp</span>
        </h2>
      </div>
      <UserFilter @filter="handleFilter" />
      <UserTable :data-source="userTypeData" :loading="isLoading || isFetching" :pagination-params="params"
        :total-pages="totalPages" @update:pagination-params="handlePaginationChange"
        @handleOpenModalUpdate="handleOpenModalUpdate"
        @handleConfirmChangeStastus="handleConfirmChangeStastus" />
      <UpdateUserModal :open="open" @handleClose="handleClose" @cancel="open = false"
        :UserDetail="userrDetail || null" :is-loading-detail="isLoadingDetail || false" />
    </div>
  </template>
  
  <script lang="ts" setup>
  import UpdateUserModal from "@/views/admin/user/UserModel.vue";
  import UserFilter from "@/views/admin/user/UserFilter.vue";
  import UserTable from  "@/views/admin/user/UserTable.vue";
  import { ParamsGetUser, UserResponse } from "@/services/api/user/user.api";
  import { useDetailUser, useGetUser, useChangeStastusUser } from "@/services/service/user/user.action";
  import { keepPreviousData } from "@tanstack/vue-query";
  import { computed, ref } from "vue";
  import { toast } from "vue3-toastify";
  
  const params = ref<ParamsGetUser>({
    page: 1,
    size: 10,
    search: ""
  });
  
  const open = ref(false);
  
  const id = ref<string | null>(null);
  
  const { data, isLoading, isFetching } = useGetUser(params, {
    refetchOnWindowFocus: false,
    placeholderData: keepPreviousData,
  });
  
  const { data: dataDetail, isLoading: isLoadingDetail } = useDetailUser(
    id,
    {
      refetchOnWindowFocus: false,
      enabled: () => !!id.value,
    }
  );
  
  const handlePaginationChange = (newParams: ParamsGetUser) => {
    params.value = { ...params.value, ...newParams };
  };
  
  const handleFilter = (newParams: ParamsGetUser) => {
    params.value = { ...params.value, ...newParams };
  };
  
  
  const { mutate: changeStastusUser } = useChangeStastusUser();
  
  const handleConfirmChangeStastus = async (id: string) => {
    console.log(id)
    try {
      await changeStastusUser(id);
      toast.success(
        "thay đổi trạng thái người dùng thành công"
      );
    } catch (error) {
      console.error("Error change status user :", error);
    }
  };

  const handleClose = () => {
    open.value = false;
    id.value = null;
  };
  
  const handleOpenModalUpdate = (record: UserResponse) => {
    id.value = record.id;
    open.value = true;
  };
  
  const userTypeData = computed(() => data?.value?.data?.data || []);
  const totalPages = computed(() => data?.value?.data?.totalPages || 0);
  const userrDetail = computed(() =>
    id.value
      ? {
        ...(dataDetail.value?.data || {}), 
        id: id.value,
      }
      : null
  );
  </script>
  