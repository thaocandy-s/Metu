<template>
  <div class="mt-4">
    <div class="flex justify-between items-center">
      <h2 class="p-4 flex items-center text-primary text-3xl font-semibold">
        <v-icon name="hi-view-grid" class="text-black" scale="2" />
        <span class="m-2 text-3xl text-black">Quản lý hình dạng</span>
      </h2>
    </div>

    <ShapeFilter @filter="handleFilter" />

    <ShapeTable 
    :data-source="shapeData" 
    :loading="isLoading || isFetching" 
    :pagination-params="params"
    :total-pages="totalPages" 
    @update:pagination-params="handlePaginationChange" 
    @handleOpenModalUpdate="handleOpenModalUpdate" 
    @handleOpenModalAdd="handleOpenModalAdd" 
    @handleConfirmChangeStatus="handleConfirmChangeStatus"       
    />


    <CreateUpdateShapeModal 
      :open="open" 
      @handleClose="handleClose" 
      @cancel="open = false" 
      :shapeDetail="shapeDetail || null" 
      :is-loading-detail="isLoadingDetail || false" 
    />
  </div>
</template>

<script lang="ts" setup>
import ShapeFilter from "@/views/admin/menu_style_config/ShapeFilter.vue";
import ShapeTable from "@/views/admin/menu_style_config/ShapeTable.vue";
import CreateUpdateShapeModal from "@/views/admin/menu_style_config/ShapeModel.vue";
import { ParamsGetFacility, ShapeResponse } from "@/services/api/menu_style_config/shape.api";
import { useShape, useDetailShape,useUpdateShapeByStatus } from "@/services/service/meny_style_config/shape.action";
import { keepPreviousData } from "@tanstack/vue-query";
import { computed, ref } from "vue";

const params = ref<ParamsGetFacility>({
  page: 1,
  size: 10,
  search: ""
});

const open = ref(false);
const id = ref<string | null>(null);
const { mutate: updateShapeStatus } = useUpdateShapeByStatus();

const { data, isLoading, isFetching } = useShape(params, {
  refetchOnWindowFocus: false,
  placeholderData: keepPreviousData,
});

const { data: dataDetail, isLoading: isLoadingDetail } = useDetailShape(
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
  console.log('Giá trị tìm kiếm:', params.value);
  
};

const handleClose = () => {
  open.value = false;
  id.value = null;
};

const handleOpenModalAdd = () => {
  open.value = true;
  id.value = null;
};

const handleConfirmChangeStatus = async (shapeId: string) => {
  const selectedShape = shapeData.value.find(shape => shape.shapeId == shapeId);

  if (selectedShape) {
    const newStatus = selectedShape.shapeStatus == "0" ? "1" : "0"; 
    console.log("Old Status: " + selectedShape.shapeStatus);
    console.log("New Status: " + newStatus);
    
    const payload = {
      shapeName: selectedShape.shapeName,
      shapeClassName: selectedShape.shapeClassName,
      shapeDescription: selectedShape.shapeDescription,
      shapeIconUrl: selectedShape.shapeIconUrl,
      shapeStatus: newStatus,
    };

    try {
      await updateShapeStatus({
        id: selectedShape.shapeId,
        params: payload,
      });

    } catch (error) {
      console.error("Lỗi khi cập nhật trạng thái vào cơ sở dữ liệu:", error); 
    }
  }
};



const handleOpenModalUpdate = (record: ShapeResponse) => {
  id.value = record.shapeId;
  open.value = true;
};  

const shapeData = computed(() => {
  const allShapes = data?.value?.data?.data || [];
  const searchTerm = params.value.search.toLowerCase();

  return allShapes.filter(shape => 
    shape.shapeCode.toLowerCase().includes(searchTerm) || 
    shape.shapeName.toLowerCase().includes(searchTerm) || 
    shape.shapeClassName.toLowerCase().includes(searchTerm)
  );
});
const totalPages = computed(() => data?.value?.data?.totalPages || 0);
const shapeDetail = computed(() =>
  id.value
    ? {
      ...dataDetail.value?.data,
      id: id.value,
    }
    : null
    
);
</script>
