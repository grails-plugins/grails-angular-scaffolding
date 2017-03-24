<%= domainImports.join('\n') %>

export class ${className} {
  id: number;

  <%= domainProperties.collect { k,v -> "${k}: ${v};" }.join('\n  ') %>

  constructor (object?: any) {
    if (object) {
      <% domainConstructorInitializingStatements.keySet().each { String key -> %>
      if (object.hasOwnProperty('${key}')) {
        <%= domainConstructorInitializingStatements.get(key).join('\n        ') %>
      }
      <% } %>
      for (var prop in object) {
        this[prop] = object[prop];
      }
    }

  }

  toString(): string {
    return '${packageName}.${className} : ' + (this.id ? this.id : '(unsaved)');
  }
}