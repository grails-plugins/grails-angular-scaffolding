package org.grails.plugin.scaffolding.angular2.registry

import org.grails.plugin.scaffolding.angular.markup.AngularPropertyMarkupRenderer
import org.grails.plugin.scaffolding.angular.registry.output.*
import org.grails.plugin.scaffolding.angular2.registry.input.AngularAssociationInputRenderer
import org.grails.plugin.scaffolding.angular2.registry.input.AngularBidirectionalToManyInputRenderer
import org.grails.plugin.scaffolding.angular2.registry.input.AngularBooleanInputRenderer
import org.grails.plugin.scaffolding.angular2.registry.input.AngularCurrencyInputRenderer
import org.grails.plugin.scaffolding.angular2.registry.input.AngularFileInputRenderer
import org.grails.plugin.scaffolding.angular2.registry.input.AngularTimeZoneInputRenderer
import org.grails.plugin.scaffolding.angular2.registry.output.AngularDateOutputRenderer
import org.grails.plugin.scaffolding.angular2.registry.output.AngularDefaultOutputRenderer
import org.grails.plugin.scaffolding.angular2.registry.output.AngularIdOutputRenderer
import org.grails.plugin.scaffolding.angular2.registry.output.AngularToManyOutputRenderer
import org.grails.plugin.scaffolding.angular2.registry.output.AngularToOneOutputRenderer
import org.grails.scaffolding.registry.DomainInputRendererRegistry
import org.grails.scaffolding.registry.DomainOutputRendererRegistry
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.annotation.Resource

class AngularDomainRendererRegisterer {

    @Autowired
    DomainInputRendererRegistry domainInputRendererRegistry

    @Autowired
    DomainOutputRendererRegistry domainOutputRendererRegistry

    @PostConstruct
    void registerRenderers() {

        domainInputRendererRegistry.registerDomainRenderer(new AngularAssociationInputRenderer(), 0)
        domainInputRendererRegistry.registerDomainRenderer(new AngularFileInputRenderer(), 0)
        domainInputRendererRegistry.registerDomainRenderer(new AngularBidirectionalToManyInputRenderer(), 0)

        domainOutputRendererRegistry.registerDomainRenderer(new AngularDefaultOutputRenderer(), 0)
        domainOutputRendererRegistry.registerDomainRenderer(new AngularDateOutputRenderer(), 0)
        domainOutputRendererRegistry.registerDomainRenderer(new AngularIdOutputRenderer(), 0)
        domainOutputRendererRegistry.registerDomainRenderer(new AngularToManyOutputRenderer(), 0)
        domainOutputRendererRegistry.registerDomainRenderer(new AngularToOneOutputRenderer(), 0)
    }
}
