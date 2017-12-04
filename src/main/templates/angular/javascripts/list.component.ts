import {Component, OnInit} from '@angular/core';
import {${className}} from '../core/${propertyName}/${propertyName}';
import {${className}Service} from '../core/${propertyName}/${propertyName}.service';

@Component({
  selector: '${propertyName}-list',
  templateUrl: './${propertyName}-list.component.html'
})
export class ${className}ListComponent implements OnInit {

  ${propertyName}List: ${className}[] = [];

  constructor(private ${propertyName}Service: ${className}Service) { }

  ngOnInit() {
    this.${propertyName}Service.list().subscribe((${propertyName}List: ${className}[]) => {
      this.${propertyName}List = ${propertyName}List;
    });
  }
}
