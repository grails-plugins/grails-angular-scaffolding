package org.grails.plugin.scaffolding.angular.registry.output

import org.grails.scaffolding.model.property.DomainProperty

/**
 * Created by Jim on 5/25/2016.
 */
class AngularDefaultOutputRenderer extends AngularDomainOutputRenderer {

    @Override
    boolean supports(DomainProperty property) {
        true
    }

    @Override
    protected Closure renderOutput(String propertyName, String propertyPath) {
        { ->
            span("{{${propertyPath}}}")
        }
    }

}
