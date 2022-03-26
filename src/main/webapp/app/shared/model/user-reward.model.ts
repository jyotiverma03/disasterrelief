import { BadgeLevel } from 'app/shared/model/enumerations/badge-level.model';

export interface IUserReward {
  id?: string;
  rating?: number | null;
  userId?: string | null;
  badgeLevel?: BadgeLevel | null;
}

export const defaultValue: Readonly<IUserReward> = {};
