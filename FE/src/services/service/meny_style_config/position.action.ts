import { queryKey } from "@/constants/queryKey";
import {
    addPosition,
    CreateUpdatePositionParams,
    getAllPosition,
    getDetailPosition,
    ParamsGetFacility,
    updatePosition,
    updatePositionByStatus
} from "@/services/api/menu_style_config/position.api";
import {
    useMutation,
    useQuery,
    useQueryClient,
    UseQueryReturnType,
} from "@tanstack/vue-query";
import { Ref } from "vue";

export const usePosition = (params: Ref<ParamsGetFacility>,
    option? : any)
    : UseQueryReturnType<Awaited<ReturnType<typeof getAllPosition>>, Error> => {
    return useQuery({
        queryKey: [queryKey.admin.position.positionList, params],
        queryFn: () => getAllPosition(params),
        ...option
    });
};

export const useCreatePosition = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: (params: CreateUpdatePositionParams) => addPosition(params),
        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: [queryKey.admin.position.positionList],
            });
        },
        onError: (error) => {
            console.log("🚀 ~ useCreatePosition ~ error:", error);
        },
    });
};

export const useUpdatePosition = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({
            params,
            id,
        }: {
            params: CreateUpdatePositionParams;
            id: string;
        }) => updatePosition(params, id),
        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: [queryKey.admin.position.positionList],
            });
        },
        onError: (error) => {
            console.log("🚀 ~ useUpdatePosition ~ error:", error);
        },
    });
};

export const useUpdatePositionByStatus = () => {
    const queryClient = useQueryClient();
  
    return useMutation({
      mutationFn: ({ id, params }: { id: string; params: CreateUpdatePositionParams }) => 
        updatePositionByStatus(params, id),
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: [queryKey.admin.position.positionList] });
      },
      onError: (error) => {
        console.error("Lỗi khi cập nhật vị trí:", error); 
      },
    });
  };

  

export const useDetailPosition = (
    id: Ref<string | null>,
    options?: any
): UseQueryReturnType<Awaited<ReturnType<typeof getDetailPosition>>, Error> => {
    return useQuery({
        queryKey: [queryKey.admin.position.positionList, id],
        queryFn: () => getDetailPosition(id.value),
        ...options,
        
    });

};
