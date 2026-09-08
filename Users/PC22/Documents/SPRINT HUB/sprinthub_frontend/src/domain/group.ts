export interface Member {
  role: "admin" | "collaborator" | "visitor";
  user: string;
}

export interface Group {
  _id: string;
  name: string;
  ownerId: string; 
  members: Member[];
  description?: string | null;
  createdAt: Date;
  updatedAt: Date;
}
