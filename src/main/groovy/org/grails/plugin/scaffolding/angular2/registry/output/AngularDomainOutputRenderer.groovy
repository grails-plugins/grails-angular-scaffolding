package org.grails.plugin.scaffolding.angular2.registry.output

import grails.util.GrailsNameUtils
import org.grails.scaffolding.model.property.DomainProperty
import org.grails.scaffolding.registry.DomainOutputRenderer

/**
 * Created by Jim on 5/25/2016.
 */
abstract class AngularDomainOutputRenderer implements DomainOutputRenderer {

    protected String buildPropertyPath(DomainProperty property) {
        StringBuilder sb = new StringBuilder()
        sb.append(getPropertyName(property)).append('.')
        sb.append(property.pathFromRoot)
        sb.toString()
    }

    protected String getPropertyName(DomainProperty property) {
        GrailsNameUtils.getPropertyName(property.rootBeanType)
    }

    @Override
    Closure renderListOutput(DomainProperty property) {
        renderOutput(property)
    }

    @Override
    Closure renderOutput(DomainProperty property) {
        StringBuilder sb = new StringBuilder()
        sb.append(GrailsNameUtils.getPropertyName(property.rootBeanType)).append('.')
        sb.append(property.pathFromRoot)
        sb.toString()
        renderOutput(getPropertyName(property), buildPropertyPath(property))
    }

    abstract protected Closure renderOutput(String propertyName, String propertyPath)

}
