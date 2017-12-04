import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {${className}} from '../core/${propertyName}/${propertyName}';
import {${className}Service} from '../core/${propertyName}/${propertyName}.service';

@Component({
  selector: '${propertyName}-persist',
  templateUrl: './${propertyName}-show.component.html'
})
export class ${className}ShowComponent implements OnInit {

  ${propertyName} = new ${className}();

  constructor(private route: ActivatedRoute, private ${propertyName}Service: ${className}Service, private router: Router) {}

  ngOnInit() {
    this.route.params.subscribe((params: Params) => {
      this.${propertyName}Service.get(+params['id']).subscribe((${propertyName}: ${className}) => {
        this.${propertyName} = ${propertyName};
      });
    });
  }

  destroy() {
    if (confirm("Are you sure?")) {
      this.${propertyName}Service.destroy(this.${propertyName}).subscribe((success: boolean) => {
        if (success) {
          this.router.navigate(['/${propertyName}','list']);
        } else {
          alert("Error occurred during delete");
        }
      });
    }
  }

}
