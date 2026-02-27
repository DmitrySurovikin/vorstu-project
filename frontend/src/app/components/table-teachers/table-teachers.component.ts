import { Component, OnInit } from '@angular/core';
import { BaseServiceService } from 'src/app/service/base-service.service';
import { Teacher } from 'src/app/models/teacher';
import { MatTableDataSource } from '@angular/material/table';
import { Group } from 'src/app/models/group';

@Component({
  selector: 'app-table-teachers',
  templateUrl: './table-teachers.component.html',
  styleUrls: ['./table-teachers.component.scss']
})
export class TableTeachersComponent implements OnInit {
  displayedColumns: string[] = ['id', 'fio', 'actions'];
  dataSource = new MatTableDataSource<Teacher>();

  constructor(private baseService: BaseServiceService) {}

  ngOnInit(): void {
    this.loadTeachers();
  }

  loadTeachers() {
    this.baseService.getAllTeachers().subscribe(data => {
      this.dataSource.data = data;
    });
  }

  //для теста так пока
  deleteTeacher(id: number) {
    if(confirm('Удалить преподавателя?')) {
        this.baseService.deleteTeacher(id).subscribe(() => {
            this.loadTeachers();
        }, error => {
            alert('Ошибка удаления (возможно нет прав)');
        });
    }
  }

  assignGroup(teacherId: number) {
    // 1. Спрашиваем ID группы (простой вариант для теста)
    const groupIdStr = prompt('Введите ID группы, которую нужно назначить (например, 1):');

    if (groupIdStr) {
      const groupId = parseInt(groupIdStr, 10);

      // Создаем объект группы с ID (имя не важно, бэк ищет по ID)
      const groupObj: Group = { id: groupId, name: '' };

      // 2. Вызываем сервис
      this.baseService.addGroupToTeacher(teacherId, groupObj).subscribe({
        next: (updatedTeacher) => {
          alert('Группа успешно назначена!');
          // Тут можно обновить список, если нужно отображать группы в таблице
          this.loadTeachers();
        },
        error: (err) => {
          alert('Ошибка! Возможно, такой группы нет или нет прав.');
          console.error(err);
        }
      });
    }
  }
}
