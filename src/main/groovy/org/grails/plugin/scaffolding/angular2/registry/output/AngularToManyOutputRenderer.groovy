package org.grails.plugin.scaffolding.angular2.registry.output

import grails.util.GrailsNameUtils
import org.grails.datastore.mapping.model.types.ToMany
import org.grails.scaffolding.model.property.DomainProperty

/**
 * Created by Jim on 5/25/2016.
 */
class AngularToManyOutputRenderer extends AngularDomainOutputRenderer {

    @Override
    boolean supports(DomainProperty property) {
        property.persistentProperty instanceof ToMany
    }

    @Override
    protected String getPropertyName(DomainProperty property) {
        GrailsNameUtils.getPropertyName(property.associatedType)
    }

    @Override
    protected Closure renderOutput(String propertyName, String propertyPath) {
        return { ->
            ul {
                li(['*ngFor': "let $propertyName of $propertyName in ${propertyPath}"]) {
                    a("{{${propertyName}.toString()}}", ["[routerLink]": "['${propertyName}', 'show', '${propertyName}.id']"])
                }
            }
        }
    }
}
