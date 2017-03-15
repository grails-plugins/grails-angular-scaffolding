import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {${className}Service} from './${propertyName}.service';
<% if (!associatedModule) { %>
import {${className}RoutingModule} from './${propertyName}-routing.module';
import {${className}ShowComponent} from './${propertyName}-show.component';
import {${className}ListComponent} from './${propertyName}-list.component';
import {${className}PersistComponent} from './${propertyName}-persist.component';
<% } %>
@NgModule({
  declarations: [<% if (!associatedModule) { %>
    ${className}ListComponent,
    ${className}PersistComponent,
    ${className}ShowComponent
  <% } %>],
  imports: [
    CommonModule,
    FormsModule,<% if (!associatedModule) { %>
    ${className}RoutingModule<% } %>
  ],
  providers: [
    ${className}Service
  ],
  exports: [
    ${className}Service
  ]
})
export class ${className}Module {}