package org.grails.plugin.scaffolding.angular2.registry.output

import grails.util.GrailsNameUtils
import org.grails.datastore.mapping.model.types.ToOne
import org.grails.scaffolding.model.property.DomainProperty

/**
 * Created by Jim on 5/25/2016.
 */
class AngularToOneOutputRenderer extends AngularDomainOutputRenderer {

    @Override
    boolean supports(DomainProperty property) {
        property.persistentProperty instanceof ToOne
    }

    @Override
    protected String getPropertyName(DomainProperty property) {
        GrailsNameUtils.getPropertyName(property.associatedType)
    }

    @Override
    protected Closure renderOutput(String propertyName, String propertyPath) {
        return { ->
            a("{{${propertyPath}.toString()}}", ["[routerLink]": "['${propertyName}','show', '${propertyPath}.id']"])
        }
    }
}
