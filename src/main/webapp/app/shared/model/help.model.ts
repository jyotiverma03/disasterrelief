import { IUser } from 'app/shared/model/user.model';
import { IAssistance } from 'app/shared/model/assistance.model';

export interface IHelp {
  id?: string;
  firstName?: string;
  lastName?: string;
  mobileNo?: string | null;
  helps?: IUser | null;
  helps?: IAssistance | null;
}

export const defaultValue: Readonly<IHelp> = {};
