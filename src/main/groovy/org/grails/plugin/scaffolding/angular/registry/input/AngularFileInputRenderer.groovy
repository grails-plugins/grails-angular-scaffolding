package org.grails.plugin.scaffolding.angular.registry.input

import grails.util.GrailsNameUtils
import org.grails.scaffolding.model.property.DomainProperty
import org.grails.scaffolding.registry.input.FileInputRenderer

/**
 * Created by Jim on 5/24/2016.
 */
class AngularFileInputRenderer extends FileInputRenderer {

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
    Closure renderInput(Map defaultAttributes, DomainProperty property) {
        defaultAttributes.put("(change)", buildPropertyPath(property) + ' = $event.srcElement.files[0]')
        defaultAttributes.remove('[(ngModel)]')
        super.renderInput(defaultAttributes, property)
    }
}
