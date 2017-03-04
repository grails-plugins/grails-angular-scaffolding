import {NgModule} from '@angular/core';
import {RouterModule,Routes} from '@angular/router';
import {${className}ListComponent} from './${propertyName}-list.component';
import {${className}PersistComponent} from './${propertyName}-persist.component';
import {${className}ShowComponent} from './${propertyName}-show.component';

const routes: Routes = [
  {path: '${propertyName}', redirectTo: '${propertyName}/list', pathMatch: 'full'},
  {path: '${propertyName}/list', component: ${className}ListComponent},
  {path: '${propertyName}/create', component: ${className}PersistComponent},
  {path: '${propertyName}/edit/:id', component: ${className}PersistComponent},
  {path: '${propertyName}/show/:id', component: ${className}ShowComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ${className}RoutingModule {}