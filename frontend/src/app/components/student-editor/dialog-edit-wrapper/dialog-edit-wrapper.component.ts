import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Student } from 'src/app/models/student';

@Component({
  selector: 'app-dialog-edit-wrapper',
  templateUrl: './dialog-edit-wrapper.component.html',
  styleUrls: ['./dialog-edit-wrapper.component.scss']
})
export class DialogEditWrapperComponent implements OnInit {

  editingStudent!: Student;

  constructor(
    public dialogRef: MatDialogRef<DialogEditWrapperComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Student | null
  ) { }

  ngOnInit(): void {
    if (this.data && this.data.id) {
      this.editingStudent = { ...this.data };
    } else {
      this.editingStudent = new Student();
    }

    if (!this.editingStudent.group) {
      this.editingStudent.group = { id: 0, name: "" };
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
