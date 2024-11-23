import { PREFIX_API_ADMIN } from "@/constants/url";
import request from "@/services/request";
import {  DefaultResponse, PaginationParams, PaginationResponse, ResponseList} from "@/utils/types/api.common";
import { AxiosResponse } from "axios";
import { Ref } from "vue";

export interface ParamsGetFacility extends PaginationParams {
  search: string
}

export type PositionResponse = ResponseList & {
  stt: string,
  positionId: string,
  positionCode: string,
  positionName: string,
  positionClassName: string,
  positionIconUrl: string,
  positionStatus: string,
  positionDescription: string,
};

export interface CreateUpdatePositionParams{
  positionName: string,
  positionClassName: string,
  positionIconUrl: string,
  positionDescription: string,
  positionStatus: string,
};

export type DetailPositionResponse = {
  positionId: string,
  positionCode: string,
  positionName: string,
  positionClassName: string,
  positionIconUrl: string,
  positionStatus: string,
  positionDescription: string,
};

export const getAllPosition = async (params: Ref<ParamsGetFacility>) => {
  const res = (await request({
    url: `${PREFIX_API_ADMIN}/position`,
    method: "GET",
    params: params.value,
  })) as AxiosResponse<DefaultResponse<PaginationResponse<Array<PositionResponse>>>>;

  return res.data;
}

export const addPosition = async (params: CreateUpdatePositionParams) => {
  const res = (await request({
    url: `${PREFIX_API_ADMIN}/position`,
    method: "POST",
    data: params,
  })) as AxiosResponse<DefaultResponse<null>>;

  return res.data;
}

export const updatePosition = async (params: CreateUpdatePositionParams, positionId : string) => {
  const res = (await request({
    url: `${PREFIX_API_ADMIN}/position/${positionId}`,
    method: "PUT",
    data: params,
  })) as AxiosResponse<DefaultResponse<null>>;

  return res.data;
}

export const updatePositionByStatus = async (params: CreateUpdatePositionParams, positionId : string) => {
  const res = (await request({
    url: `${PREFIX_API_ADMIN}/position/${positionId}/status`,
    method: "PUT",
    data: params,
  })) as AxiosResponse<DefaultResponse<null>>;

  return res.data;
}

export const getDetailPosition = async (shapeId: string | null) => {
  const res = (await request({
    url: `${PREFIX_API_ADMIN}/position/${shapeId}`,
    method: "GET",
  })) as AxiosResponse<DefaultResponse<DetailPositionResponse>>;
  
  return res.data;
  };