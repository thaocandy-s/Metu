import { queryKey } from "@/constants/queryKey";

import {
    useMutation,
    useQuery,
    useQueryClient,
    UseQueryReturnType,
} from "@tanstack/vue-query";
import { Ref } from "vue";

import {
   UpdateUserParams,
   ParamsGetUser,
   changeStatusUser,
   detailUser,
   getAllUser,
   updateUser,
} from "@/services/api/user/user.api";

export const useGetUser = (params: Ref<ParamsGetUser>,
    options?: any)
    : UseQueryReturnType<Awaited<ReturnType<typeof getAllUser>>, Error> => {
    return useQuery({
        queryKey: [queryKey.admin.UserType.usertypeList, params],
        queryFn: () => getAllUser(params),
        ...options
    });
};

export const useUpdateUser = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({
            id,
            params,
        }: {
            id: string;
            params: UpdateUserParams;
        }) => updateUser(params, id),
        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: [queryKey.admin.UserType.usertypeList],
            });
        },
        onError: (error) => {
            console.log("🚀 ~ useUpdateUser ~ error:", error);
        },
    });
};

export const useChangeStastusUser = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: (id: string) => changeStatusUser(id),
        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: [queryKey.admin.UserType.usertypeList],
            });
        },
        onError: (error) => {
            console.log("🚀 ~ useChangeStatusUser ~ error:", error);
        },
    });
};

export const useDetailUser = (
    id: Ref<string | null>,
    options?: any
): UseQueryReturnType<Awaited<ReturnType<typeof detailUser>>, Error> => {
    return useQuery({
        queryKey: [queryKey.admin.UserType.usertypeList, id],
        queryFn: () => detailUser(id.value),
        ...options,
    });
};