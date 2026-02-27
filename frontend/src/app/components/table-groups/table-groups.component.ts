import { Component, OnInit } from '@angular/core';
import { BaseServiceService } from 'src/app/service/base-service.service';
import { Group } from 'src/app/models/group';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-table-groups',
  templateUrl: './table-groups.component.html',
  styleUrls: ['./table-groups.component.scss']
})
export class TableGroupsComponent implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'actions'];
  dataSource = new MatTableDataSource<Group>();

  constructor(private baseService: BaseServiceService) { }

  ngOnInit(): void {
    this.loadGroups();
  }

  loadGroups() {
    this.baseService.getAllGroups().subscribe(data => {
      this.dataSource.data = data;
    });
  }

  createGroup() {
    const groupName = prompt('Введите название новой группы:');
    if (groupName) {
      const newGroup: Group = { id: null, name: groupName };
      this.baseService.createGroup(newGroup).subscribe(() => {
        this.loadGroups();
      }, err => alert('Ошибка при создании группы'));
    }
  }

  deleteGroup(id: number) {
    if(confirm('Вы уверены? Это удалит группу и отвяжет от неё всех студентов.')) {
      this.baseService.deleteGroup(id).subscribe(() => {
        this.loadGroups();
      });
    }
  }
}
