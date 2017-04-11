package org.grails.plugin.scaffolding.angular.registry

import org.grails.plugin.scaffolding.angular.registry.input.AngularAssociationInputRenderer
import org.grails.plugin.scaffolding.angular.registry.input.AngularBidirectionalToManyInputRenderer
import org.grails.plugin.scaffolding.angular.registry.input.AngularDateInputRenderer
import org.grails.plugin.scaffolding.angular.registry.input.AngularFileInputRenderer
import org.grails.plugin.scaffolding.angular.registry.output.*
import org.grails.scaffolding.registry.DomainInputRendererRegistry
import org.grails.scaffolding.registry.DomainOutputRendererRegistry
import org.springframework.beans.factory.annotation.Autowired
import javax.annotation.PostConstruct

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
        domainInputRendererRegistry.registerDomainRenderer(new AngularDateInputRenderer(), 0)

        domainOutputRendererRegistry.registerDomainRenderer(new AngularDefaultOutputRenderer(), 0)
        domainOutputRendererRegistry.registerDomainRenderer(new AngularDateOutputRenderer(), 0)
        domainOutputRendererRegistry.registerDomainRenderer(new AngularIdOutputRenderer(), 0)
        domainOutputRendererRegistry.registerDomainRenderer(new AngularToManyOutputRenderer(), 0)
        domainOutputRendererRegistry.registerDomainRenderer(new AngularToOneOutputRenderer(), 0)
    }
}
