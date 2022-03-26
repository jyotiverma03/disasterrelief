export interface IAddress {
  id?: string;
  street1?: string | null;
  street2?: string | null;
  city?: string | null;
  state?: string | null;
  pinCode?: string | null;
  country?: string | null;
  latitude?: string | null;
  longitude?: string | null;
}

export const defaultValue: Readonly<IAddress> = {};
