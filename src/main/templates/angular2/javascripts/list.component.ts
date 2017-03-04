import {Component, OnInit} from '@angular/core';
import {${className}Service} from './${propertyName}.service';
import {${className}} from './${propertyName}';

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
