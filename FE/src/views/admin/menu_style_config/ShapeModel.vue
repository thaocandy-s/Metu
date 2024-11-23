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
              <img v-if="modelRef.shapeIconUrl" :src="modelRef.shapeIconUrl" alt="Icon Preview" style="max-width: 100px; max-height: 100px; border-radius: 4px; margin-top: 8px;" />
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
  useCreateShape,
  useUpdateShape,
} from "@/services/service/meny_style_config/shape.action";
import { Form } from "ant-design-vue";
import { computed, reactive, watch } from "vue";
import { toast } from "vue3-toastify";

interface ShapeForm {
  shapeName: string;
  shapeDescription: string;
  shapeClassName: string;
  shapeIconUrl: string;  
  shapeStatus: string;
}

const props = defineProps({
  open: Boolean,
  shapeDetail: Object as () => any | null,
  isLoadingDetail: Boolean,
});

const emit = defineEmits(["handleClose"]);

const { mutate: createShape } = useCreateShape();
const { mutate: updateShape } = useUpdateShape();

const modelRef = reactive<ShapeForm>({
  shapeName: "",
  shapeClassName: "",
  shapeDescription: "",
  shapeIconUrl: "",
  shapeStatus: "0",
});

const rulesRef = reactive({
  shapeName: [
    { required: true, message: "Vui lòng nhập tên hình dạng", trigger: "blur" },
    { min: 3, max: 255, message: "Tên hình dạng phải từ 3 đến 255 ký tự", trigger: "blur" },
  ],
  shapeDescription: [
    { required: false, message: "Vui lòng nhập mô tả", trigger: "blur" },
    { max: 500, message: "Mô tả không được vượt quá 500 ký tự", trigger: "blur" },
  ],
  shapeClassName: [
    { required: true, message: "Vui lòng nhập class name", trigger: "blur" },
  ],
  shapeIconUrl: [
    { required: true, message: "Vui lòng nhập URL icon hoặc tải lên ảnh", trigger: "blur" }
  ]
});

const { resetFields, validate, validateInfos } = Form.useForm(
  modelRef,
  rulesRef
);

const modalTitle = computed(() =>
  props.shapeDetail ? "Cập nhật hình dạng" : "Thêm hình dạng"
);

const okText = computed(() =>
  props.shapeDetail ? "Cập nhật" : "Thêm mới"
);

watch(
  () => props.shapeDetail,
  (newVal) => {

    if (newVal) {
      Object.assign(modelRef, {
        shapeName: newVal.shapeName,
        shapeDescription: newVal.shapeDescription,
        shapeClassName: newVal.shapeClassName,
        shapeIconUrl: newVal.shapeIconUrl,
      });
    } else {
      resetFields();
    }
  },
  { immediate: true }
);

const formFields = computed(() => [
  {
    label: "Tên hình dạng",
    name: "shapeName",
    component: "a-input",
    props: { placeholder: "Nhập tên hình dạng" },
  },
  {
    label: "Mô tả",
    name: "shapeDescription",
    component: "a-textarea",
    props: { placeholder: "Nhập mô tả", rows: 4 },
  },
  {
    label: "Class Name",
    name: "shapeClassName",
    component: "a-input",
    props: { placeholder: "Nhập class name của hình dạng" },
  },
  {
    label: "Icon URL",
    name: "shapeIconUrl",
    component: "a-input",
    props: { placeholder: "Nhập URL icon của hình dạng" },
  },
]);


const handleAddOrUpdate = async () => {
  const payload = {
    shapeName: modelRef.shapeName,
    shapeClassName: modelRef.shapeClassName,
    shapeDescription: modelRef.shapeDescription,
    shapeIconUrl: modelRef.shapeIconUrl,
    shapeStatus: modelRef.shapeStatus,
  };

  try {
    await validate(); 
    if (props.shapeDetail) {
      await updateShape({
        params: payload,
        id: props.shapeDetail.id,
      });
    } else {
      await createShape(payload);
    }
    toast.success(
      props.shapeDetail
        ? "Cập nhật hình dạng thành công"
        : "Thêm hình dạng thành công"
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