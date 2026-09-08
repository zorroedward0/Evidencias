export interface User {
  _id: string;
  name: string;
  email: string;
  documentId: string;
  passwordHash: string;
  role: "admin" | "user";
  createdAt: Date;
  updatedAt: Date;
}
