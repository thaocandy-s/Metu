import { queryKey } from "@/constants/queryKey";
import {
    addShape,
    CreateUpdateShapeParams,
    getAllShape,
    getDetailShape,
    ParamsGetFacility,
    updateShape,
    updateShapeByStatus
} from "@/services/api/menu_style_config/shape.api";
import {
    useMutation,
    useQuery,
    useQueryClient,
    UseQueryReturnType,
} from "@tanstack/vue-query";
import { Ref } from "vue";

export const useShape = (params: Ref<ParamsGetFacility>,
    option? : any)
    : UseQueryReturnType<Awaited<ReturnType<typeof getAllShape>>, Error> => {
    return useQuery({
        queryKey: [queryKey.admin.shape.shapeList, params],
        queryFn: () => getAllShape(params),
        ...option
    });
};

export const useCreateShape = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: (params: CreateUpdateShapeParams) => addShape(params),
        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: [queryKey.admin.shape.shapeList],
            });
        },
        onError: (error) => {
            console.log("🚀 ~ useCreateShape ~ error:", error);
        },
    });
};

export const useUpdateShape = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({
            params,
            id,
        }: {
            params: CreateUpdateShapeParams;
            id: string;
        }) => updateShape(params, id),
        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: [queryKey.admin.shape.shapeList],
            });
        },
        onError: (error) => {
            console.log("🚀 ~ useUpdateShape ~ error:", error);
        },
    });
};

export const useUpdateShapeByStatus = () => {
    const queryClient = useQueryClient();
  
    return useMutation({
      mutationFn: ({ id, params }: { id: string; params: CreateUpdateShapeParams }) => 
        updateShapeByStatus(params, id),
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: [queryKey.admin.shape.shapeList] });
      },
      onError: (error) => {
        console.error("Lỗi khi cập nhật trạng thái:", error); 
      },
    });
  };

  

export const useDetailShape = (
    id: Ref<string | null>,
    options?: any
): UseQueryReturnType<Awaited<ReturnType<typeof getDetailShape>>, Error> => {
    return useQuery({
        queryKey: [queryKey.admin.shape.shapeList, id],
        queryFn: () => getDetailShape(id.value),
        ...options,
        
    });

};
