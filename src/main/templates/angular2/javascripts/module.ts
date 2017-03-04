import {${className}ListComponent} from './${propertyName}-list.component';
import {${className}PersistComponent} from './${propertyName}-persist.component';
import {CommonModule} from '@angular/common';
import {${className}RoutingModule} from './${propertyName}-routing.module';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {${className}Service} from './${propertyName}.service';
import {${className}ShowComponent} from './${propertyName}-show.component';

@NgModule({
  declarations: [
    ${className}ListComponent,
    ${className}PersistComponent,
    ${className}ShowComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ${className}RoutingModule
  ],
  providers: [
    ${className}Service
  ]
})
export class ${className}Module {}