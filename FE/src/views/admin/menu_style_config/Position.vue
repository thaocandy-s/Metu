<template>
  <div class="mt-4">
    <div class="flex justify-between items-center">
      <h2 class="p-4 flex items-center text-primary text-3xl font-semibold">
        <v-icon name="hi-view-grid" class="text-black" scale="2" />
        <span class="m-2 text-3xl text-black">Quản lý vị trí</span>
      </h2>
    </div>

    <PositionFilter @filter="handleFilter" />

    <PositionTable 
    :data-source="positionData" 
    :loading="isLoading || isFetching" 
    :pagination-params="params"
    :total-pages="totalPages" 
    @update:pagination-params="handlePaginationChange" 
    @handleOpenModalUpdate="handleOpenModalUpdate" 
    @handleOpenModalAdd="handleOpenModalAdd" 
    @handleConfirmChangeStatus="handleConfirmChangeStatus"       
    />


    <CreateUpdatePositionModal 
      :open="open" 
      @handleClose="handleClose" 
      @cancel="open = false" 
      :positionDetail="positionDetail || null" 
      :is-loading-detail="isLoadingDetail || false" 
    />
  </div>
</template>

<script lang="ts" setup>
import PositionFilter from "@/views/admin/menu_style_config/PositionFilter.vue";
import PositionTable from "@/views/admin/menu_style_config/PositionTable.vue";
import CreateUpdatePositionModal from "@/views/admin/menu_style_config/PositionModel.vue";
import { ParamsGetFacility, PositionResponse } from "@/services/api/menu_style_config/position.api";
import { usePosition, useDetailPosition,useUpdatePositionByStatus } from "@/services/service/meny_style_config/position.action";
import { keepPreviousData } from "@tanstack/vue-query";
import { computed, ref } from "vue";

const params = ref<ParamsGetFacility>({
  page: 1,
  size: 10,
  search: ""
});

const open = ref(false);
const id = ref<string | null>(null);
const { mutate: updatePositionStatus } = useUpdatePositionByStatus();

const { data, isLoading, isFetching } = usePosition(params, {
  refetchOnWindowFocus: false,
  placeholderData: keepPreviousData,
});

const { data: dataDetail, isLoading: isLoadingDetail } = useDetailPosition(
  id,
  {
    refetchOnWindowFocus: false,
    enabled: () => !!id.value,
  }
);

const handlePaginationChange = (newParams: ParamsGetFacility) => {
  params.value = { ...params.value, ...newParams };
};

const handleFilter = (newParams: ParamsGetFacility) => {
  params.value = { ...params.value, ...newParams };  
};

const handleClose = () => {
  open.value = false;
  id.value = null;
};

const handleOpenModalAdd = () => {
  open.value = true;
  id.value = null;
};

const handleConfirmChangeStatus = async (positionId: string) => {
  const selectedPosition = positionData.value.find(position => position.positionId == positionId);

  if (selectedPosition) {
    const newStatus = selectedPosition.positionStatus == "0" ? "1" : "0"; 
    
    const payload = {
      positionName: selectedPosition.positionName,
      positionClassName: selectedPosition.positionClassName,
      positionDescription: selectedPosition.positionDescription,
      positionIconUrl: selectedPosition.positionIconUrl,
      positionStatus: newStatus,
    };

    try {
      await updatePositionStatus({
        id: selectedPosition.positionId,
        params: payload,
      });

    } catch (error) {
      console.error("Lỗi khi cập nhật trạng thái vào cơ sở dữ liệu:", error); 
    }
  }
};

const handleOpenModalUpdate = (record: PositionResponse) => {
  id.value = record.positionId;
  open.value = true;
};  

const positionData = computed(() => {
  const allPositions = data?.value?.data?.data || [];
  const searchTerm = params.value.search.toLowerCase();

  return allPositions.filter(position => 
    position.positionCode.toLowerCase().includes(searchTerm) || 
    position.positionName.toLowerCase().includes(searchTerm) || 
    position.positionClassName.toLowerCase().includes(searchTerm)
  );
});
const totalPages = computed(() => data?.value?.data?.totalPages || 0);
const positionDetail = computed(() =>
  id.value
    ? {
      ...dataDetail.value?.data,
      id: id.value,
    }
    : null
    
);
</script>
