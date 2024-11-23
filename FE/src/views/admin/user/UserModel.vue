<template>
    <div>
        <a-modal :open="open" :title="modalTitle" @cancel="handleClose" @ok="handleUpdate" :ok-text="okText"
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
                </a-form>
            </div>
        </a-modal>
    </div>
</template>

<script lang="ts" setup>
import { ERROR_MESSAGE } from "@/constants/message.constant";
import {
   useUpdateUser
} from "@/services/service/user/user.action";
import { Form } from "ant-design-vue";
import { computed, reactive, watch } from "vue";
import { toast } from "vue3-toastify";

interface UserForm {
    name: string;
    email: string;
    address: string;
}

const props = defineProps({
    open: Boolean,
    UserDetail: Object as () => any | null,
    isLoadingDetail: Boolean,
});

const emit = defineEmits(["handleClose"]);


const { mutate: updateUser } = useUpdateUser();

const modelRef = reactive<UserForm>({
    name: "",
    email: "",
    address: "",
});

const rulesRef = reactive({
    name: [
        { required: true, message: "Vui lòng nhập tên người dùng", trigger: "blur" },
        { min: 5, max: 255, message: "Tên người dùng phải từ 5 đến 255 ký tự", trigger: "blur" },
    ],
    email: [
        { required: false, message: "Vui lòng nhập email", trigger: "blur" },
        { max: 500, message: "Email không được vượt quá 500 ký tự", trigger: "blur" },
        { 
            validator: (rule, value) => {
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (!value || emailRegex.test(value)) {
                    return Promise.resolve();
                } else {
                    return Promise.reject("Email không đúng định dạng");
                }
            },
            trigger: "blur"
        }
    ],
    address: [
        { required: false, message: "Vui lòng nhập địa chỉ", trigger: "blur" },
        { max: 500, message: "Địa chỉ không được vượt quá 500 ký tự", trigger: "blur" },
    ],
});

const { resetFields, validate, validateInfos } = Form.useForm(
    modelRef,
    rulesRef
);

const modalTitle = computed(() => "Cập nhật người dùng");

const okText = computed(() => "Cập nhật người dùng");

watch(
    () => props.UserDetail,
    (newVal) => {
        if (newVal) {
            Object.assign(modelRef, {
                name: newVal.name,
                email: newVal.email,
                address: newVal.address,
            });
        } else {
            resetFields();
        }
    },
    { immediate: true }
);


const formFields = computed(() => [
    {
        label: "Tên người dùng",
        name: "name",
        component: "a-input",
        props: { placeholder: "Nhập tên người dùng" },
    },
    {
        label: "Email",
        name: "email",
        component: "a-input",
        props: { placeholder: "Nhập email" },
    },
    {
        label: "Địa chỉ",
        name: "address",
        component: "a-input",
        props: { placeholder: "Nhập địa chỉ" },
    },
]);

const handleUpdate = async () => {
    const payload = {
        name: modelRef.name,
        email: modelRef.email,
        address: modelRef.address,
    };

    try {
        await validate();
        if (props.UserDetail) {
            await updateUser({
                id: props.UserDetail.id,
                params: payload,
            });
        } 
        toast.success(
            "Cập nhật thể loại doanh nghiệp thành công"
        );
        emit("handleClose");
    } catch (error: any) {
        console.error("🚀 ~ handleUpdate ~ error:", error);
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