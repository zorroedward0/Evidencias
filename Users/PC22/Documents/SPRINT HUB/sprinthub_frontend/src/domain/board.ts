export interface IBoard {
  _id: string;
  title: string;
  groupId: string;
  ownerId: string;
  description?: string | null;
  createdAt: Date;
  updatedAt: Date;
}

