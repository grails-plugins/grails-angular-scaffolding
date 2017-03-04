export class ${className} {
  id: number;

  constructor (object?: any) {
    for (var prop in object) {
      this[prop] = object[prop];
    }
  }

  toString(): string {
    return '${packageName}.${className} : ' + (this.id ? this.id : '(unsaved)');
  }
}