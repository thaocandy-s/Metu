import { PREFIX_API_ADMIN } from "@/constants/url";
import request from "@/services/request";
import {  DefaultResponse, PaginationParams, PaginationResponse, ResponseList} from "@/utils/types/api.common";
import { AxiosResponse } from "axios";
import { Ref } from "vue";

export interface ParamsGetFacility extends PaginationParams {
  search: string
}

export type ShapeResponse = ResponseList & {
  stt: string,
  shapeId: string,
  shapeCode: string,
  shapeName: string,
  shapeClassName: string,
  shapeIconUrl: string,
  shapeStatus: string,
  shapeDescription: string,
};

export interface CreateUpdateShapeParams{
  shapeName: string,
  shapeClassName: string,
  shapeIconUrl: string,
  shapeDescription: string,
  shapeStatus: string,
};

export type DetailShapeResponse = {
  shapeId: string,
  shapeCode: string,
  shapeName: string,
  shapeClassName: string,
  shapeIconUrl: string,
  shapeStatus: string,
  shapeDescription: string,
};

export const getAllShape = async (params: Ref<ParamsGetFacility>) => {
  const res = (await request({
    url: `${PREFIX_API_ADMIN}/shape`,
    method: "GET",
    params: params.value,
  })) as AxiosResponse<DefaultResponse<PaginationResponse<Array<ShapeResponse>>>>;

  return res.data;
}

export const addShape = async (params: CreateUpdateShapeParams) => {
  const res = (await request({
    url: `${PREFIX_API_ADMIN}/shape`,
    method: "POST",
    data: params,
  })) as AxiosResponse<DefaultResponse<null>>;

  return res.data;
}

export const updateShape = async (params: CreateUpdateShapeParams, shapeID : string) => {
  const res = (await request({
    url: `${PREFIX_API_ADMIN}/shape/${shapeID}`,
    method: "PUT",
    data: params,
  })) as AxiosResponse<DefaultResponse<null>>;

  return res.data;
}

export const updateShapeByStatus = async (params: CreateUpdateShapeParams, shapeID : string) => {
  const res = (await request({
    url: `${PREFIX_API_ADMIN}/shape/${shapeID}/status`,
    method: "PUT",
    data: params,
  })) as AxiosResponse<DefaultResponse<null>>;

  return res.data;
}

export const getDetailShape = async (shapeId: string | null) => {
const res = (await request({
  url: `${PREFIX_API_ADMIN}/shape/${shapeId}`,
  method: "GET",
})) as AxiosResponse<DefaultResponse<DetailShapeResponse>>;

return res.data;
};