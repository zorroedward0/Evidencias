export interface Task {
  title: string;
  completed: boolean;
}

export interface Card {
  _id: string;
  position: number;
  title: string;
  columnId: string;
  priority: "alta" | "media" | "baja";
  tasks: Task[];
  description?: string | null;
  assignedTo?: string | null;
  dueDate?: Date | null;
  createdAt: Date;
  updatedAt: Date;
}
