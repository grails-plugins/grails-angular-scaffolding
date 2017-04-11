package org.grails.plugin.scaffolding.angular.registry.output

import grails.util.GrailsNameUtils
import org.grails.datastore.mapping.model.types.ToMany
import org.grails.scaffolding.model.property.DomainProperty
import org.grails.scaffolding.registry.DomainOutputRenderer

/**
 * Created by Jim on 5/25/2016.
 */
class AngularToManyOutputRenderer implements DomainOutputRenderer, PropertyHelper {

    @Override
    Closure renderListOutput(DomainProperty property) {
        renderOutput(property)
    }

    @Override
    Closure renderOutput(DomainProperty property) {
        String propertyPath = buildPropertyPath(property)
        String propertyName = GrailsNameUtils.getPropertyName(property.associatedType)
        return { ->
            ul {
                li(['*ngFor': "let $propertyName of ${propertyPath}"]) {
                    a("{{${propertyName}.toString()}}", ["[routerLink]": "['/${propertyName}', 'show', ${propertyName}.id]"])
                }
            }
        }
    }

    @Override
    boolean supports(DomainProperty property) {
        property.persistentProperty instanceof ToMany
    }

}
