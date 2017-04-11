package org.grails.plugin.scaffolding.angular.registry.input

import org.grails.scaffolding.model.property.DomainProperty
import org.grails.scaffolding.registry.input.DateInputRenderer

/**
 * Created by jameskleeh on 3/20/17.
 */
class AngularDateInputRenderer extends DateInputRenderer {

    @Override
    Closure renderInput(Map defaultAttributes, DomainProperty property) {
        defaultAttributes.type = "date"
        defaultAttributes.placeholder = "YYYY-MM-DD"
        String model = defaultAttributes.remove("[(ngModel)]")
        defaultAttributes.put("[value]", "$model | date:'y-MM-dd'")
        defaultAttributes.put("(change)", "$model = \$event.target.valueAsDate")
        return { ->
            input(defaultAttributes)
        }
    }
}
