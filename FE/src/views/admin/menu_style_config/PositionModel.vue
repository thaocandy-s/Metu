<template>
    <div>
      <a-modal :open="open" :title="modalTitle" @cancel="handleClose" @ok="handleAddOrUpdate" :ok-text="okText"
          destroyOnClose centered>
        <div v-if="props.isLoadingDetail" class="flex justify-center items-center">
          <a-spin />
        </div>
        <div v-else>
          <a-form layout="vertical">
            <template v-for="field in formFields">
              <a-form-item :label="field.label" :name="field.name" v-bind="validateInfos[field.name]">
                <component :is="field.component" v-bind="field.props" v-model:value="modelRef[field.name]">
                </component>
              </a-form-item>
            </template>
  
            <a-form-item label="Tải lên Icon">
              <a-form-item label="Xem trước Icon">
                <div style="display: flex; flex-direction: column; align-items: center;">
                  <img v-if="modelRef.positionIconUrl" :src="modelRef.positionIconUrl" alt="Icon Preview" style="max-width: 100px; max-height: 100px; border-radius: 4px; margin-top: 8px;" />
                  <div v-else style="margin-top: 8px;">
                    <v-icon name="hi-plus" scale="2" />
                    <div>Chưa có icon</div>
                  </div>
                </div>
              </a-form-item>
            </a-form-item>
  
          </a-form>
        </div>
      </a-modal>
    </div>
  </template>
  
  
  <script lang="ts" setup>
  import { ERROR_MESSAGE } from "@/constants/message.constant";
  import {
    useCreatePosition,
    useUpdatePosition,
  } from "@/services/service/meny_style_config/position.action";
  import { Form } from "ant-design-vue";
  import { computed, reactive, watch } from "vue";
  import { toast } from "vue3-toastify";
  
  interface PositionForm {
    positionName: string;
    positionDescription: string;
    positionClassName: string;
    positionIconUrl: string;  
    positionStatus: string;
  }
  
  const props = defineProps({
    open: Boolean,
    positionDetail: Object as () => any | null,
    isLoadingDetail: Boolean,
  });
  
  const emit = defineEmits(["handleClose"]);
  
  const { mutate: createPosition } = useCreatePosition();
  const { mutate: updatePosition } = useUpdatePosition();
  
  const modelRef = reactive<PositionForm>({
    positionName: "",
    positionClassName: "",
    positionDescription: "",
    positionIconUrl: "",
    positionStatus: "0",
  });
  
  const rulesRef = reactive({
    positionName: [
      { required: true, message: "Vui lòng nhập tên vị trí", trigger: "blur" },
      { min: 3, max: 255, message: "Tên vị trí phải từ 3 đến 255 ký tự", trigger: "blur" },
    ],
    positionDescription: [
      { required: false, message: "Vui lòng nhập mô tả", trigger: "blur" },
      { max: 500, message: "Mô tả không được vượt quá 500 ký tự", trigger: "blur" },
    ],
    positionClassName: [
      { required: true, message: "Vui lòng nhập class name", trigger: "blur" },
    ],
    positionIconUrl: [
      { required: true, message: "Vui lòng nhập URL icon hoặc tải lên ảnh", trigger: "blur" }
    ]
  });
  
  const { resetFields, validate, validateInfos } = Form.useForm(
    modelRef,
    rulesRef
  );
  
  const modalTitle = computed(() =>
    props.positionDetail ? "Cập nhật vị trí" : "Thêm vị trí"
  );
  
  const okText = computed(() =>
    props.positionDetail ? "Cập nhật" : "Thêm mới"
  );
  
  watch(
    () => props.positionDetail,
    (newVal) => {
  
      if (newVal) {
        Object.assign(modelRef, {
          positionName: newVal.positionName,
          positionDescription: newVal.positionDescription,
          positionClassName: newVal.positionClassName,
          positionIconUrl: newVal.positionIconUrl,
        });
      } else {
        resetFields();
      }
    },
    { immediate: true }
  );
  
  const formFields = computed(() => [
    {
      label: "Tên vị trí",
      name: "positionName",
      component: "a-input",
      props: { placeholder: "Nhập tên vị trí" },
    },
    {
      label: "Mô tả",
      name: "positionDescription",
      component: "a-textarea",
      props: { placeholder: "Nhập mô tả", rows: 4 },
    },
    {
      label: "Class Name",
      name: "positionClassName",
      component: "a-input",
      props: { placeholder: "Nhập class name của vị trí" },
    },
    {
      label: "Icon URL",
      name: "positionIconUrl",
      component: "a-input",
      props: { placeholder: "Nhập URL icon của vị trí" },
    },
  ]);
  
  
  const handleAddOrUpdate = async () => {
    const payload = {
      positionName: modelRef.positionName,
      positionClassName: modelRef.positionClassName,
      positionDescription: modelRef.positionDescription,
      positionIconUrl: modelRef.positionIconUrl,
      positionStatus: modelRef.positionStatus,
    };
  
    try {
      await validate(); 
      if (props.positionDetail) {
        await updatePosition({
          params: payload,
          id: props.positionDetail.id,
        });
      } else {
        await createPosition(payload);
      }
      toast.success(
        props.positionDetail
          ? "Cập nhật vị trí thành công"
          : "Thêm vị trí thành công"
      );
      emit("handleClose");
    } catch (error: any) {
      console.error("🚀 ~ handleAddOrUpdate ~ error:", error);
      toast.error(
        error?.response?.data?.message || ERROR_MESSAGE.SOMETHING_WENT_WRONG
      );
    }
  };
  
  const handleClose = () => {
    emit("handleClose");
    resetFields();
  };
  </script>