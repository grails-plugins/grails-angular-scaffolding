package angular.scaffolding

import grails.codegen.model.Model
import grails.dev.commands.GrailsApplicationCommand
import grails.util.GrailsNameUtils
import grails.web.mapping.UrlMappings
import grails.web.mapping.exceptions.UrlMappingException
import org.grails.datastore.mapping.model.MappingContext
import org.grails.datastore.mapping.model.PersistentEntity
import org.grails.datastore.mapping.model.PersistentProperty
import org.grails.datastore.mapping.model.types.Association
import org.grails.datastore.mapping.model.types.ManyToOne
import org.grails.datastore.mapping.model.types.ToMany
import org.grails.plugin.scaffolding.angular.template.AngularModuleEditor
import org.grails.scaffolding.markup.DomainMarkupRenderer
import org.grails.scaffolding.model.DomainModelService
import org.grails.scaffolding.model.property.DomainProperty
import org.grails.scaffolding.registry.input.BooleanInputRenderer
import org.grails.scaffolding.registry.input.CurrencyInputRenderer
import org.grails.scaffolding.registry.input.FileInputRenderer
import org.grails.scaffolding.registry.input.TimeZoneInputRenderer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value

class NgGenerateAllCommand implements GrailsApplicationCommand {

    boolean skipBootstrap = true

    private MappingContext grailsDomainClassMappingContext

    @Autowired
    void setGrailsDomainClassMappingContext(@Qualifier('grailsDomainClassMappingContext') MappingContext mappingContext) {
        this.grailsDomainClassMappingContext = mappingContext
    }

    DomainModelService domainModelService
    DomainMarkupRenderer domainMarkupRenderer
    UrlMappings grailsUrlMappingsHolder
    AngularModuleEditor angularModuleEditor


    private PersistentEntity domainClass

    @Value('${grails.codegen.angular.baseDir:../client/src/app}')
    String baseDir

    @Value('${grails.codegen.angular.bootstrapModule:app.module.ts}')
    String bootstrapModule

    boolean handle() {

        String domainClassName = args[0]

        boolean overwrite = (args[1] instanceof String && args[1].toLowerCase() == "true")

        try {
            domainClass = grailsDomainClassMappingContext.getPersistentEntity(domainClassName)
        } catch (e) { }

        if (!domainClass) {
            domainClass = grailsDomainClassMappingContext.getPersistentEntities().find { it.javaClass.simpleName == domainClassName }
        }

        if (!domainClass) {
            System.err.println("Error | The domain class you entered: \"${domainClassName}\" could not be found")
            return false
        }

        Model module = model(domainClass.javaClass)

        String formTemplate = domainMarkupRenderer.renderInput(domainClass)
        String showTemplate = domainMarkupRenderer.renderOutput(domainClass)
        String listTemplate = domainMarkupRenderer.renderListOutput(domainClass)

        Map htmlTemplates = [persist: formTemplate, list: listTemplate, show: showTemplate]

        String uri = getUri(module)

        FileInputRenderer fileInputRenderer = new FileInputRenderer()
        CurrencyInputRenderer currencyInputRenderer = new CurrencyInputRenderer()
        TimeZoneInputRenderer timeZoneInputRenderer = new TimeZoneInputRenderer()
        BooleanInputRenderer booleanInputRenderer = new BooleanInputRenderer()
        List<DomainProperty> associatedProperties = []
        Boolean hasFileProperty = false
        List<String> initializingStatements = []
        List<String> constructorArguments = []
        List<String> componentProperties = []
        List<String> componentImports = []
        Map<String, String> domainProperties = [:]
        Map<String, List<String>> domainConstructorInitializingStatements = [:]
        List<String> domainImports = []
        List<String> routeParams = []

        domainModelService.getInputProperties(domainClass).each { DomainProperty property ->
            PersistentProperty prop = property.persistentProperty
            domainProperties.put(property.name, 'any')
            if (prop instanceof Association) {
                String type = property.associatedType.simpleName
                String name = GrailsNameUtils.getPropertyName(type)
                associatedProperties.add(property)

                boolean owningSide = prop.owningSide
                if (prop instanceof ManyToOne) {
                    owningSide = false
                }

                if (!prop.bidirectional || !owningSide) {
                    constructorArguments.add("private ${name}Service: ${type}Service")
                    initializingStatements.add("this.${name}Service.list().subscribe((${name}List: ${type}[]) => { this.${name}List = ${name}List; });")
                    routeParams.add("""
      if (params.hasOwnProperty('${name}Id')) {
        this.${module.propertyName}.${property.name} = new $type({id: params['${name}Id']})
      }
""")
                    componentProperties.add("${name}List: ${type}[];")
                    componentImports.add("import { ${type}Service } from '../core/${name}/${name}.service';")
                    componentImports.add("import { ${type} } from '../core/${name}/${name}';")
                }
                domainImports.add("import { ${type} } from '../${name}/${name}';")

                String initializingStatement
                if (prop instanceof ToMany) {
                    domainProperties.put(property.name, "$type[]")
                    initializingStatement = "this.${property.name} = object['${property.name}'].map((obj: any) => { return new ${type}(obj); });"
                } else {
                    domainProperties.put(property.name, type)
                    initializingStatement = "this.${property.name} = new ${type}(object['${property.name}']);"
                }
                domainConstructorInitializingStatements.put(property.name, [initializingStatement, "delete object['${property.name}'];"])
            } else {
                if (fileInputRenderer.supports(property)) {
                    hasFileProperty = true
                } else if (currencyInputRenderer.supports(property)) {
                    initializingStatements.add("this.${module.propertyName}.${property.name} = '${currencyInputRenderer.getOptionKey(currencyInputRenderer.getDefaultOption())}';")
                } else if (timeZoneInputRenderer.supports(property)) {
                    initializingStatements.add("this.${module.propertyName}.${property.name} = '${timeZoneInputRenderer.getOptionKey(timeZoneInputRenderer.getDefaultOption())}';")
                } else if (booleanInputRenderer.supports(property)) {
                    initializingStatements.add("this.${module.propertyName}.${property.name} = false;")
                    domainProperties.put(property.name, 'boolean')
                } else {
                    if (property.type == String) {
                        domainProperties.put(property.name, 'string')
                    } else if (Number.isAssignableFrom(property.type)) {
                        domainProperties.put(property.name, 'number')
                    }
                }
            }
        }

        File coreModule = file("${baseDir}/core/core.module.ts")
        Model coreModel = model("Core")
        if (!coreModule.exists()) {
            render template: template("angular/javascripts/module.ts"),
                    destination: coreModule,
                    model: [className: "Core", associatedModule: true],
                    overwrite: false
        }

        render template: template(hasFileProperty ? "angular/javascripts/service-file.ts" : "angular/javascripts/service.ts"),
                destination: file("${baseDir}/core/${module.propertyName}/${module.propertyName}.service.ts"),
                model: module.asMap() << [uri: uri],
                overwrite: overwrite

        render template: template("angular/javascripts/domain.ts"),
                destination: file("${baseDir}/core/${module.propertyName}/${module.propertyName}.ts"),
                model: module.asMap() << [domainProperties: domainProperties, domainConstructorInitializingStatements: domainConstructorInitializingStatements, domainImports: domainImports],
                overwrite: overwrite

        File moduleFile = file("${baseDir}/${module.propertyName}/${module.propertyName}.module.ts")
        render template: template("angular/javascripts/module.ts"),
                destination: moduleFile,
                model: module.asMap() << [associatedModule: false],
                overwrite: overwrite

        render template: template("angular/javascripts/routing.module.ts"),
                destination: file("${baseDir}/${module.propertyName}/${module.propertyName}-routing.module.ts"),
                model: module,
                overwrite: overwrite

        ['persist', 'list', 'show'].each {
            render template: template("angular/javascripts/${it}.component.ts"),
                    destination: file("${baseDir}/${module.propertyName}/${module.propertyName}-${it}.component.ts"),
                    model: module.asMap() << [initializingStatements: initializingStatements, constructorArguments: constructorArguments, componentProperties: componentProperties, componentImports: componentImports, routeParams: routeParams],
                    overwrite: overwrite

            render template: template("angular/views/${it}.component.html"),
                    destination: file("${baseDir}/${module.propertyName}/${module.propertyName}-${it}.component.html"),
                    model: module.asMap() << [template: htmlTemplates.get(it)],
                    overwrite: overwrite

        }

        File appModule = new File(baseDir, bootstrapModule)

        if (appModule.exists() && appModule.isFile()) {
            if (angularModuleEditor.addDependency(appModule, module, '.')) {
                println("Added ${module.className} as a dependency to your bootstrap module")
            } else {
                println("Warning | An error occurred importing the ${module.className} module into your bootstrap module")
            }
        } else {
            println("Warning | Bootstrap module ${baseDir}/${bootstrapModule} not found. You will have to import the ${module.className} module yourself")
        }

        if (angularModuleEditor.addDependency(moduleFile, coreModel, '..')) {
            println("Added ${coreModel.className} as a dependency to your ${module.propertyName} module")
        } else {
            println("Warning | An error occurred importing the ${coreModel.className} module into your ${module.propertyName} module")
        }

        angularModuleEditor.addProvider(coreModule, module, '.')


        associatedProperties.each { DomainProperty prop ->

            Model associatedModel = model(prop.associatedType)

            uri = getUri(associatedModel)

            render template: template("angular/javascripts/domain.ts"),
                    destination: file("${baseDir}/core/${associatedModel.propertyName}/${associatedModel.propertyName}.ts"),
                    model: associatedModel.asMap() << [domainProperties: [:], domainConstructorInitializingStatements: [:], domainImports: []],
                    overwrite: false

            Association association = (Association) prop.persistentProperty
            boolean owningSide = association.owningSide
            if (association instanceof ManyToOne) {
                owningSide = false
            }

            if (!association.bidirectional || !owningSide) {
                render template: template("angular/javascripts/service.ts"),
                        destination: file("${baseDir}/core/${associatedModel.propertyName}/${associatedModel.propertyName}.service.ts"),
                        model: associatedModel.asMap() << [uri: uri],
                        overwrite: false

                if (angularModuleEditor.addProvider(coreModule, associatedModel, '.')) {
                    println("Added ${associatedModel.className} as a dependency to your core module")
                } else {
                    println("Warning | An error occurred importing the ${associatedModel.className} module into your core module")
                }

            }

        }

        true

    }

    String getUri(Model model) {
        try {
            grailsUrlMappingsHolder
                    .getReverseMapping(model.propertyName, "index", null, null, "GET", Collections.emptyMap())
                    .createRelativeURL(model.propertyName, "index", [:], 'UTF8')
                    .replaceFirst('/', '')
                    .replace('/index', '')
        } catch (UrlMappingException e) {
            model.propertyName
        }
    }

}
