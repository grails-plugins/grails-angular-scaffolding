import {Injectable} from "@angular/core";
import {Http, Response, RequestOptions, RequestMethod, Request, Headers} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {${className}} from "./${propertyName}";
import {Subject} from "rxjs/Subject";

import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/of';

@Injectable()
export class ${className}Service {

  private baseUrl = 'http://localhost:8080/';

  constructor(private http: Http) {
  }

  list(): Observable<${className}[]> {
    let subject = new Subject<${className}[]>();
    this.http.get(this.baseUrl + '${uri}')
      .map((r: Response) => r.json())
      .subscribe((json: any[]) => {
        subject.next(json.map((item: any) => new ${className}(item)))
      });
    return subject.asObservable();
  }

  get(id: number): Observable<${className}> {
    return this.http.get(this.baseUrl + '${uri}/'+id)
      .map((r: Response) => new ${className}(r.json()));
  }

  save(${propertyName}: ${className}): Observable<${className}> {
    let headers = new Headers();
    headers.append("Accept", 'application/json');
    headers.delete("Content-Type");
    headers.append('X-Requested-With', 'XMLHttpRequest');

    let formData: FormData = new FormData();

    for (let property in ${propertyName}) {
      if (${propertyName}.hasOwnProperty(property)) {
        formData.append(property, ${propertyName}[property]);
      }
    }

    let requestOptions = new RequestOptions({
      headers: headers,
      body: formData
    });

    if (${propertyName}.id) {
      requestOptions.method = RequestMethod.Put;
      requestOptions.url = this.baseUrl + '${uri}/' + ${propertyName}.id;
    } else {
      requestOptions.method = RequestMethod.Post;
      requestOptions.url = this.baseUrl + '${uri}';
    }

    return this.http.request(new Request(requestOptions))
      .map((r: Response) => new ${className}(r.json()));
  }

  destroy(${propertyName}: ${className}): Observable<boolean> {
    return this.http.delete(this.baseUrl + '${uri}/' + ${propertyName}.id).map((res: Response) => res.ok).catch(() => {
      return Observable.of(false);
    });
  }
}