package org.grails.plugin.scaffolding.angular

import grails.plugins.Plugin
import org.grails.plugin.scaffolding.angular.markup.AngularContextMarkupRenderer
import org.grails.plugin.scaffolding.angular.markup.AngularPropertyMarkupRenderer
import org.grails.plugin.scaffolding.angular.registry.AngularDomainRendererRegisterer
import org.grails.plugin.scaffolding.angular.template.AngularModuleEditorImpl
import org.grails.scaffolding.ScaffoldingBeanConfiguration

class AngularScaffoldingGrailsPlugin extends Plugin {


    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.2.0 > *"

    // TODO Fill in these fields
    def title = "Grails Angular Scaffolding" // Headline output name of the plugin
    def author = "James Kleeh"
    def authorEmail = "kleehj@ociweb.com"
    def description = '''\
This plugin provides the ability to generate an Angular CRUD interface based on a domain class
'''
    String documentation = 'http://grails-plugins.github.io/grails-angular-scaffolding/latest'
    String license = 'APACHE'
    def organization = [name: 'Grails', url: 'http://www.grails.org/']
    def issueManagement = [url: 'https://github.com/grails-plugins/grails-angular-scaffolding/issues']
    def scm = [url: 'https://github.com/grails-plugins/grails-angular-scaffolding']

    Closure doWithSpring() {{ ->
        scaffoldingCoreConfig(ScaffoldingBeanConfiguration)

        propertyMarkupRenderer(AngularPropertyMarkupRenderer)

        contextMarkupRenderer(AngularContextMarkupRenderer)

        angularModuleEditor(AngularModuleEditorImpl)

        angularDomainRendererRegisterer(AngularDomainRendererRegisterer)
    }}
}
