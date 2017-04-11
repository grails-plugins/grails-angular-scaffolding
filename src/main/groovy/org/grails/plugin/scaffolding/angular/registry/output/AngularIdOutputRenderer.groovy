package org.grails.plugin.scaffolding.angular.registry.output

import org.grails.scaffolding.model.property.DomainProperty

/**
 * Created by Jim on 5/26/2016.
 */
class AngularIdOutputRenderer extends AngularDomainOutputRenderer {

    @Override
    boolean supports(DomainProperty property) {
        property.domainClass.isIdentityName(property.name)
    }

    @Override
    Closure renderListOutput(DomainProperty property) {
        final String propertyPath = buildPropertyPath(property)
        return { ->
            a("{{${propertyPath}}}", ["[routerLink]": "['/${getPropertyName(property)}','show',${propertyPath}]"])
        }
    }

    @Override
    protected Closure renderOutput(String propertyName, String propertyPath) {
        { ->
            span("{{${propertyPath}}}")
        }
    }
}
