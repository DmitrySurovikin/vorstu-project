import { Component, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { fromEvent, merge, of, BehaviorSubject } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, startWith, switchMap, map } from 'rxjs/operators';
import { Student } from 'src/app/models/student';
import { BaseServiceService, StudentsApiResponse } from 'src/app/service/base-service.service';
import { DialogEditWrapperComponent } from '../student-editor/dialog-edit-wrapper/dialog-edit-wrapper.component';
// import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-table-students',
  templateUrl: './table-students.component.html',
  styleUrls: ['./table-students.component.scss']
})
export class TableStudentsComponent implements AfterViewInit {

  //свойства класса
  displayedColumns: string[] = ['id', 'fio', 'group', 'phoneNumber', 'actions'];
  dataSource = new MatTableDataSource<Student>([]); // данные для таблицы (с пустого массива), ну грубо говоря мост между массивом и html-табличкой

  resultsLength = 0;
  isLoadingResults = true; // загрузкоу

  // ссылочки на html
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild('filterInput') filterInput!: ElementRef; // поле ввода

  // ------

  // принудительное обновление данных (спусковой крючёк, можно так выразиться)
  private refreshData = new BehaviorSubject<boolean>(true);

  constructor(
    private baseService: BaseServiceService,
    public dialog: MatDialog
  ) {}

  // ------

  ngAfterViewInit() {

    this.localizePaginator();
    // для сортировочки
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);

    // оператор RxJS, объединяет несколько потоклв событий в 1 поток
    merge(
      this.sort.sortChange,
      this.paginator.page,
      fromEvent(this.filterInput.nativeElement, 'keyup'),
      this.refreshData
    )

    .pipe(
      // пустое событие для старта
      startWith({}),
      // ожидание, от передоза запросов
      debounceTime(300),
      // всё также, чтоб лишних запросов не было
      distinctUntilChanged(),
      // отмена старого запроса(если новый поступил)
      switchMap(() => {
        this.isLoadingResults = true;
        return this.baseService.findStudents(
          this.paginator.pageIndex,
          this.paginator.pageSize,
          this.filterInput.nativeElement.value,
          this.sort.active,
          this.sort.direction
        ).pipe(
          catchError(() => of(null))
        );
      }),

      // преобразование результата
      map(data => {
        this.isLoadingResults = false;

        if (data === null) {
          return [];
        }

        this.resultsLength = data.totalElements;
        return data.content;
      })
    ).subscribe(data => {
      this.dataSource.data = data;


    });
  }

  private localizePaginator() {
    const paginatorIntl = this.paginator._intl;

    paginatorIntl.itemsPerPageLabel = 'Записей на странице:';
    paginatorIntl.nextPageLabel = 'Следующая страница';
    paginatorIntl.previousPageLabel = 'Предыдущая страница';
    paginatorIntl.firstPageLabel = 'Первая страница';
    paginatorIntl.lastPageLabel = 'Последняя страница';

    paginatorIntl.getRangeLabel = (page: number, pageSize: number, length: number): string => {
      if (length === 0 || pageSize === 0) {
        return `0 из ${length}`;
      }
      length = Math.max(length, 0);
      const startIndex = page * pageSize;
      const endIndex = startIndex < length ? Math.min(startIndex + pageSize, length) : startIndex + pageSize;
      return `${startIndex + 1} – ${endIndex} из ${length}`;
    };

    this.paginator._intl.changes.next();
  }

  // ------

  addNewStudent() {
    const dialogRef = this.dialog.open(DialogEditWrapperComponent, {width: '400px', data: null});
    dialogRef.afterClosed().subscribe((result: Student) => {
      if (result) {
        this.baseService.addNewStudent(result).subscribe(() => {
          this.refreshData.next(true);
        });
      }
    });
  }

  updateStudent(student: Student) {
    const dialogRef = this.dialog.open(DialogEditWrapperComponent, {width: '400px', data: { ...student }});
    dialogRef.afterClosed().subscribe((result: Student) => {
      if (result && result.id) {
        this.baseService.updateStudent(result.id, result).subscribe(() => {
          console.log('АААААУУУУЭЭЭ   КОЛОБКИИИИ ЭЭЭЭЭ');
          this.refreshData.next(true);
        });
      }
    });
  }

  deleteStudent(student: Student): void {
    // const dialogRef = this.dialog.open(ConfirmationDialogComponent, ...);
    const dialogRef = this.dialog.open(DialogEditWrapperComponent, {width: '400px', data: {} });
    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed && student.id) {
        this.baseService.deleteStudent(student.id).subscribe(() => {
          this.refreshData.next(true);
        });
      }
    });
  }
}

