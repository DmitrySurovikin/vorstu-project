export interface Group {
  id: number;
  name: string;
}

export class Student {
  id: number | null;
  fio: string;
  group: Group;
  phoneNumber: string;

  constructor() {
    this.id = null;
    this.fio = "";
    this.phoneNumber = "";
    this.group = { id: 0, name: "" };
  }
}
