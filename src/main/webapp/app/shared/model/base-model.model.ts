export interface IBaseModel {
  id?: string;
  create?: number | null;
  modified?: number | null;
  deleted?: number | null;
  updated?: boolean | null;
}

export const defaultValue: Readonly<IBaseModel> = {
  updated: false,
};
