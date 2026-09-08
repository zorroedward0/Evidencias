export interface Comment {
  _id: string;
  name: string;
  description: string;
  cardId: string;
  createdFor: string;
  createdAt: Date;
  updateAt: Date;
}
