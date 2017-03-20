<%= domainImports.join('\n') %>

export class ${className} {
  id: number;

  <%= domainProperties.collect { k,v -> "${k}: ${v};" }.join('\n  ') %>

  constructor (object?: any) {
    <%= domainConstructorInitializingStatements.join('\n    ') %>
    for (var prop in object) {
      this[prop] = object[prop];
    }
  }

  toString(): string {
    return '${packageName}.${className} : ' + (this.id ? this.id : '(unsaved)');
  }
}