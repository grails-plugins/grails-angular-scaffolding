package org.grails.plugin.scaffolding.angular.registry.output

import grails.util.GrailsNameUtils
import org.grails.scaffolding.model.property.DomainProperty

/**
 * Created by jameskleeh on 3/13/17.
 */
trait PropertyHelper {

    String buildPropertyPath(DomainProperty property) {
        StringBuilder sb = new StringBuilder()
        sb.append(getPropertyName(property)).append('.')
        sb.append(property.pathFromRoot)
        sb.toString()
    }

    String getPropertyName(DomainProperty property) {
        GrailsNameUtils.getPropertyName(property.rootBeanType)
    }
}