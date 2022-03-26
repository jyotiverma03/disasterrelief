export interface IEvent {
  id?: string;
  type?: string | null;
  description?: string | null;
  approved?: boolean | null;
  active?: boolean | null;
}

export const defaultValue: Readonly<IEvent> = {
  approved: false,
  active: false,
};
