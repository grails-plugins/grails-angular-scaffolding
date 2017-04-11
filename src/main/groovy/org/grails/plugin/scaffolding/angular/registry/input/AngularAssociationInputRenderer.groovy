package org.grails.plugin.scaffolding.angular.registry.input

import grails.util.GrailsNameUtils
import org.grails.datastore.mapping.model.PersistentProperty
import org.grails.datastore.mapping.model.types.Association
import org.grails.datastore.mapping.model.types.Basic
import org.grails.datastore.mapping.model.types.ToMany
import org.grails.plugin.scaffolding.angular.registry.output.PropertyHelper
import org.grails.scaffolding.model.property.DomainProperty
import org.grails.scaffolding.registry.DomainInputRenderer

class AngularAssociationInputRenderer implements DomainInputRenderer, PropertyHelper {

    boolean supports(DomainProperty property) {
        PersistentProperty persistentProperty = property.persistentProperty
        persistentProperty instanceof Association && !(persistentProperty instanceof Basic)
    }

    @Override
    Closure renderInput(Map defaultAttributes, DomainProperty property) {
        final String name = GrailsNameUtils.getPropertyName(property.associatedType)

        PersistentProperty persistentProperty = property.persistentProperty
        if (persistentProperty instanceof ToMany && !persistentProperty.bidirectional) {
            defaultAttributes["multiple"] = ""
        }

        return { ->
            select('', defaultAttributes) {
                option("{{${name}.toString()}}", ["*ngFor": "let $name of ${name}List", "[value]": "${name}.id", "[selected]": "${buildPropertyPath(property)}?.id == ${name}.id"])
            }
        }
    }
}
