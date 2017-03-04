package angular2.scaffolding

import grails.plugins.Plugin
import org.grails.scaffolding.ScaffoldingBeanConfiguration

class Angular2ScaffoldingGrailsPlugin extends Plugin {


    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.2.0 > *"

    // TODO Fill in these fields
    def title = "Grails Angular 2 Scaffolding" // Headline output name of the plugin
    def author = "James Kleeh"
    def authorEmail = "kleehj@ociweb.com"
    def description = '''\
This plugin provides the ability to generate an AngularJS CRUD interface based on a domain class
'''
    String documentation = 'http://grails-plugins.github.io/grails-angular2-scaffolding/latest'
    String license = 'APACHE'
    def organization = [name: 'Grails', url: 'http://www.grails.org/']
    def issueManagement = [url: 'https://github.com/grails-plugins/grails-angular2-scaffolding/issues']
    def scm = [url: 'https://github.com/grails-plugins/grails-angular2-scaffolding']

    Closure doWithSpring() {{ ->
        scaffoldingCoreConfig(ScaffoldingBeanConfiguration)

    }}
}
