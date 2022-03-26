import { IUser } from 'app/shared/model/user.model';

export interface IAssistance {
  id?: string;
  name?: string | null;
  description?: string | null;
  assistance?: IUser | null;
}

export const defaultValue: Readonly<IAssistance> = {};
