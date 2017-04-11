package org.grails.plugin.scaffolding.angular.markup

import org.grails.datastore.mapping.model.PersistentEntity
import org.grails.scaffolding.markup.ContextMarkupRendererImpl
import org.grails.scaffolding.model.property.DomainProperty

/**
 * Created by jameskleeh on 2/21/17.
 */
class AngularContextMarkupRenderer extends ContextMarkupRendererImpl {

    @Override
    Closure listOutputContext(PersistentEntity domainClass, List<DomainProperty> properties, Closure content) {
        final String propertyName = domainClass.decapitalizedName
        final String listName = "${propertyName}List"

        return { ->
            table {
                thead {
                    tr {
                        properties.each {
                            th(getDefaultTableHeader(it))
                        }
                    }
                }
                tbody {
                    tr(["[ngClass]": "{'even': e, 'odd': !e}", "*ngFor": "let ${propertyName} of ${listName}; let e = even;"]) {
                        properties.each {
                            td(content.call(it))
                        }
                    }
                }
            }
        }
    }
}
