package org.grails.plugin.scaffolding.angular2.markup

import grails.util.GrailsNameUtils
import org.grails.scaffolding.markup.PropertyMarkupRendererImpl
import org.grails.scaffolding.model.property.DomainProperty
import org.springframework.beans.factory.annotation.Value

/**
 * Created by jameskleeh on 2/21/17.
 */
class AngularPropertyMarkupRenderer extends PropertyMarkupRendererImpl {

    @Override
    Map getStandardAttributes(DomainProperty property) {
        final String objectName = GrailsNameUtils.getPropertyName(property.rootBeanType)
        Map attributes = super.getStandardAttributes(property)
        attributes["[(ngModel)]"] = "$objectName.${attributes.name}"
        attributes
    }
}
