import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { TableStudentsComponent } from './components/table-students/table-students.component';
import { TableTeachersComponent } from './components/table-teachers/table-teachers.component';
import { TableGroupsComponent } from './components/table-groups/table-groups.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'students', component: TableStudentsComponent },
  { path: 'teachers', component: TableTeachersComponent },
  { path: 'groups', component: TableGroupsComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
