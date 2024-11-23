import {PREFIX_API_ADMIN} from "@/constants/url";
import request from "@/services/request";
import {DefaultResponse, PaginationParams,PaginationResponse, ResponseList} from "@/utils/types/api.common";
import {AxiosResponse} from "axios";
import { Ref } from "vue";


export interface ParamsGetUser extends PaginationParams {
  search: string
}

export type UserResponse = ResponseList & {
  stt: string;
  avatarImage: string;
  code: string;
  name: string;
  nameBusiness: string;
  email: string;
  address: string;
  isOnline: boolean;
  LastTimeActive: number;
  rating: number;
  status:  String;
};

export type UpdateUserParams = {
  name: string;
  email: string;
  address: string;
};

export const getAllUser = async (params: Ref<ParamsGetUser>) => {
    const res = (await request({
        url: `${PREFIX_API_ADMIN}/user`,
        method: "GET",
        params: params.value,
    })) as AxiosResponse<DefaultResponse<PaginationResponse<Array<UserResponse>>>>;

    return res.data;
}


export const updateUser = async (params: UpdateUserParams, id: string) => {
    const res = (await request({
      url: `${PREFIX_API_ADMIN}/user/${id}`,
      method: "PUT",
      data: params,
    })) as AxiosResponse<DefaultResponse<null>>;
  
    return res.data;
  };
  
  export const detailUser = async (id: string | null) => {
    const res = (await request({
      url: `${PREFIX_API_ADMIN}/user/${id}`,
      method: "GET",
    })) as AxiosResponse<DefaultResponse<null>>;
  
    return res.data;
  };
  
export const changeStatusUser = async (id: string) => {
    const res = (await request({
      url: `${PREFIX_API_ADMIN}/user/${id}/change-status`,
      method: "PUT",
    })) as AxiosResponse<DefaultResponse<any>>;
  
    return res.data;
  };
  