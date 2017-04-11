package org.grails.plugin.scaffolding.angular.registry.output

import org.grails.datastore.mapping.model.types.ToOne
import org.grails.scaffolding.model.property.DomainProperty

class AngularToOneOutputRenderer extends AngularDomainOutputRenderer {

    @Override
    boolean supports(DomainProperty property) {
        property.persistentProperty instanceof ToOne
    }

    @Override
    protected Closure renderOutput(String propertyName, String propertyPath) {
        return { ->
            a("{{${propertyPath}.toString()}}", ["*ngIf": propertyPath, "[routerLink]": "['/${propertyName}','show', ${propertyPath}.id]"])
        }
    }
}
