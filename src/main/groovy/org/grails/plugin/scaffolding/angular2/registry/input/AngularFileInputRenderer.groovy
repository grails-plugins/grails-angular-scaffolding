package org.grails.plugin.scaffolding.angular2.registry.input

import org.grails.scaffolding.model.property.DomainProperty
import org.grails.scaffolding.registry.input.FileInputRenderer

/**
 * Created by Jim on 5/24/2016.
 */
class AngularFileInputRenderer extends FileInputRenderer {

    @Override
    Closure renderInput(Map defaultAttributes, DomainProperty property) {
        { ->
            span("File type attributes are not currently supported")
        }
    }
}
