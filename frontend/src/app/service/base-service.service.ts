import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Student } from '../models/student';
import { SortDirection } from '@angular/material/sort';
import { Teacher } from '../models/teacher';
import { Group } from '../models/group';

// описываем контракт с апишкой, говорим какого формата ждём ответ
export interface StudentsApiResponse {
  content: Student[];
  totalElements: number;
}


@Injectable({
  providedIn: 'root'
})
export class BaseServiceService {

  private studentsUrl = '/api/base/students';

  constructor(private http: HttpClient) { }


  // ------

  findStudents(
    page: number,
    limit: number,
    filterValue: string,
    sortColumn: string,
    sortDirection: SortDirection
  ): Observable<StudentsApiResponse> {

    // сборщик параметров (неизменяемый)
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', limit.toString());   // кол-во студентоа, которое нужно

    // сортировочка
    if (sortColumn && sortDirection) {
      params = params.set('sort', `${sortColumn},${sortDirection}`);
    }

    // фильрация
    if (filterValue) {
      params = params.set('fio', filterValue);
    }

    // сортировочка
    // if (sortColumn && sortDirection) {
    //   let sortByValue = sortColumn;

    //   if (sortDirection === 'desc') {
    //     sortByValue = '-' + sortColumn;
    //   }

    //   params = params.set('sortBy', sortByValue);
    // }

    return this.http.get<StudentsApiResponse>(this.studentsUrl, { params });
  }

  addNewStudent(student: Student): Observable<Student> {
    return this.http.post<Student>(this.studentsUrl, student);
  }

  updateStudent(id: number, student: Student): Observable<any> {
    return this.http.put(`${this.studentsUrl}`, student);
  }

  deleteStudent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.studentsUrl}/${id}`);
  }

  //для преподов
  getAllTeachers(): Observable<Teacher[]> {
    return this.http.get<Teacher[]>('/api/teachers');
  }

  addNewTeacher(teacher: Teacher): Observable<Teacher> {
    return this.http.post<Teacher>('/api/teachers', teacher);
  }

  updateTeacher(teacher: Teacher): Observable<Teacher> {
    return this.http.put<Teacher>('/api/teachers', teacher);
  }

  deleteTeacher(id: number): Observable<void> {
    return this.http.delete<void>(`/api/teachers/${id}`);
  }

  //группы
  getAllGroups(): Observable<Group[]> {
    return this.http.get<Group[]>('/api/groups');
  }

  createGroup(group: Group): Observable<Group> {
    return this.http.post<Group>('/api/groups', group);
  }

  deleteGroup(id: number): Observable<void> {
    return this.http.delete<void>(`/api/groups/${id}`);
  }

  addGroupToTeacher(teacherId: number, group: Group): Observable<Teacher> {
    return this.http.post<Teacher>(`/api/teachers/${teacherId}/groups`, group);
  }
}
