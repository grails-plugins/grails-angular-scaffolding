package org.grails.plugin.scaffolding.angular.registry.output

import org.grails.scaffolding.model.property.DomainProperty
import org.grails.scaffolding.registry.DomainOutputRenderer

/**
 * Created by Jim on 5/25/2016.
 */
abstract class AngularDomainOutputRenderer implements DomainOutputRenderer, PropertyHelper {

    @Override
    Closure renderListOutput(DomainProperty property) {
        renderOutput(property)
    }

    @Override
    Closure renderOutput(DomainProperty property) {
        renderOutput(getPropertyName(property), buildPropertyPath(property))
    }

    abstract protected Closure renderOutput(String propertyName, String propertyPath)

}
