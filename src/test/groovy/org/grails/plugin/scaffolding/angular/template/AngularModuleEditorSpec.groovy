package org.grails.plugin.scaffolding.angular.template

import grails.codegen.model.Model
import grails.codegen.model.ModelBuilder
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

@Subject(AngularModuleEditorImpl)
class AngularModuleEditorSpec extends Specification {

    @Shared
    File file

    @Shared
    Model model = new ModelBuilder.ModelImpl("com.foo.Bar")

    @Shared
    AngularModuleEditorImpl editor

    @Shared
    String lineEnding = System.getProperty('line.separator')

    @Shared
    String expectedImport = "import { BarModule } from './bar/bar.module';" + lineEnding

    void setup() {
        file = File.createTempFile("angularModule", "js")
        editor = new AngularModuleEditorImpl()
    }

    void cleanup() {
        file.delete()
    }

    void "test addModuleImport"() {
        given:
        file.write("""
            @NgModule({
                imports: []
            })
        """)

        when:
        boolean success = editor.addModuleImport(file, model)

        then:
        file.text == """
            @NgModule({
                imports: [
    BarModule
]
            })
        """
        success
    }

    void "test addModuleImport will append to an existing list"() {
        given:
        file.write("""
            @NgModule({
                imports: [
                    Foo
                ]
            })
        """)

        when:
        boolean success = editor.addModuleImport(file, model)

        then:
        file.text == """
            @NgModule({
                imports: [
    Foo,
    BarModule
]
            })
        """
        success
    }

    void "test addModuleImport without import key"() {
        given:
        file.write("""
            @NgModule({
            })
        """)

        when:
        boolean success = editor.addModuleImport(file, model)

        then: "the imports key will be added"
        file.text == """
            @NgModule({
imports: [
    BarModule
]
            })
        """
        success
    }

    void "test addModuleImport without import key 2"() {
        given:
        file.write("""
            @NgModule({
                providers: [FooService]
            })
        """)

        when:
        boolean success = editor.addModuleImport(file, model)

        then: "the imports key will be added"
        file.text == """
            @NgModule({
                providers: [FooService],
imports: [
    BarModule
]
            })
        """
        success
    }

    void "test addModuleImport without import key 3"() {
        given:
        file.write("""
            @NgModule({
                providers: [FooService],
            })
        """)

        when:
        boolean success = editor.addModuleImport(file, model)

        then: "the imports key will be added without double comma'ing the providers"
        file.text == """
            @NgModule({
                providers: [FooService],
imports: [
    BarModule
]
            })
        """
        success
    }

    void "test addModuleImport without import key 4"() {
        given:
        file.write("""
            @NgModule({
                providers: [FooService],
                declares: [
                   Foo,
                   Bar
                ]
            })
        """)

        when:
        boolean success = editor.addModuleImport(file, model)

        then: "the imports key will be added after the last module key"
        file.text == """
            @NgModule({
                providers: [FooService],
                declares: [
                   Foo,
                   Bar
                ],
imports: [
    BarModule
]
            })
        """
        success
    }

    void "test addModuleImport with imports in another object"() {
        given:
        file.write("""
            var obj = {imports: []};            
            @NgModule({
                imports: []
            })
        """)

        when:
        boolean success = editor.addModuleImport(file, model)

        then: "the correct array is updated"
        file.text == """
            var obj = {imports: []};            
            @NgModule({
                imports: [
    BarModule
]
            })
        """
        success
    }

    void "test addModuleImport wont duplicate dependencies"() {
        given:
        file.write("""
            @NgModule({
                imports: [
                    BarModule
                ]
            })
        """)

        when:
        boolean success = editor.addModuleImport(file, model)

        then:
        file.text == """
            @NgModule({
                imports: [
                    BarModule
                ]
            })
        """
        success
    }

    void "test addModuleImport with text before and after"() {
        given:
        file.write("""
            foo

            @NgModule({
                imports: []
            })
            bar

        """)

        when:
        boolean success = editor.addModuleImport(file, model)

        then:
        file.text == """
            foo

            @NgModule({
                imports: [
    BarModule
]
            })
            bar

        """
        success
    }

    void "test addModuleImport with multiple modules"() {
        given:
        file.write("""
            @NgModule({
                imports: []
            })
            @NgModule({
                imports: []
            })
        """)

        when:
        boolean success = editor.addModuleImport(file, model)

        then: "the first one will be updated"
        file.text == """
            @NgModule({
                imports: [
    BarModule
]
            })
            @NgModule({
                imports: []
            })
        """
        success
    }

    void "test addModuleImport with a multiple spaces before dependencies"() {
        given:
        file.write("""
            @NgModule({
                imports:     []
            })
        """)

        when:
        boolean success = editor.addModuleImport(file, model)

        then:
        file.text == """
            @NgModule({
                imports:     [
    BarModule
]
            })
        """
        success
    }

    void "test addModuleImport with invalid module but valid dependency section"() {
        given:
        file.write("""
            @NgModule(
                imports: []
            })
        """)

        when:
        boolean success = editor.addModuleImport(file, model)

        then:
        file.text == """
            @NgModule(
                imports: []
            })
        """
        !success
    }

    void "test addModuleImport with invalid module dependency"() {
        given:
        file.write("""
            @NgModule({
                imports: ]
            })
        """)

        when:
        boolean success = editor.addModuleImport(file, model)

        then:
        file.text == """
            @NgModule({
                imports: ]
            })
        """
        !success
    }

    void "test addModuleImport with invalid module dependency 2"() {
        given:
        file.write("""
            @NgModule({
                imports: [
            })
        """)

        when:
        boolean success = editor.addModuleImport(file, model)

        then:
        file.text == """
            @NgModule({
                imports: [
            })
        """
        !success
    }

    void "test addModuleImport with invalid module dependency 3"() {
        given:
        file.write("""
            @NgModule({
                imports: [);]
            })
        """)

        when:
        boolean success = editor.addModuleImport(file, model)

        then:
        file.text == """
            @NgModule({
                imports: [
    );,
    BarModule
]
            })
        """
        success
    }

    void "test addModuleImport with no existing module"() {
        given:
        file.write("""
            hello
        """)

        when:
        boolean success = editor.addModuleImport(file, model)

        then:
        file.text == """
            hello
        """
        !success
    }

    void "test addModuleImport with a trailing comma"() {
        given:
        file.write("""
            @NgModule({
                imports: [Foo,]
            })
        """)

        when:
        boolean success = editor.addModuleImport(file, model)

        then:
        file.text == """
            @NgModule({
                imports: [
    Foo,
    BarModule
]
            })
        """
        success
    }

    void "test addImport"() {
        given:
        file.write("""
            import { NavComponent } from './nav/nav.component';
            import { NavService } from './nav/nav.service';\n@NgModule({})\nexport class Foo {}
        """)

        when:
        boolean success = editor.addImport(file, model)

        then:
        file.text == """
            import { NavComponent } from './nav/nav.component';
            import { NavService } from './nav/nav.service';\n${expectedImport}@NgModule({})\nexport class Foo {}
        """
        success
    }


    void "test addImport with extra lines after last import"() {
        given:
        file.write("""
            import { NavComponent } from './nav/nav.component';
            import { NavService } from './nav/nav.service';\n\n@NgModule({})
export class Foo {}
        """)

        when:
        boolean success = editor.addImport(file, model)

        then:
        file.text == """
            import { NavComponent } from './nav/nav.component';
            import { NavService } from './nav/nav.service';\n${expectedImport}\n@NgModule({})
export class Foo {}
        """
        success
    }


    void "test addImport windows line endings"() {
        given:
        file.write("""
            import { NavComponent } from './nav/nav.component';\r\nimport { NavService } from './nav/nav.service';\r\n@NgModule({})
export class Foo {}
        """)

        when:
        boolean success = editor.addImport(file, model)

        then:
        file.text == """
            import { NavComponent } from './nav/nav.component';\r\nimport { NavService } from './nav/nav.service';\r\n${expectedImport}@NgModule({})
export class Foo {}
        """
        success
    }

    void "test addImport no imports exist"() {
        given:
        file.write("""
            @NgModule({})
export class Foo {}
        """)

        when:
        boolean success = editor.addImport(file, model)

        then: "it will add it to the beginning of the file"
        file.text == """${expectedImport}
            @NgModule({})
export class Foo {}
        """
        success
    }

    void "test addImport wont duplicate imports"() {
        given:
        file.write("""
            import { NavComponent } from './nav/nav.component';
            import { NavService } from './nav/nav.service';
                    ${expectedImport}
            @NgModule({})
export class Foo {}
        """)

        when:
        boolean success = editor.addImport(file, model)

        then:
        file.text == """
            import { NavComponent } from './nav/nav.component';
            import { NavService } from './nav/nav.service';
                    ${expectedImport}
            @NgModule({})
export class Foo {}
        """
        success
    }


    void "test addDependency"() {
        given:
        file.write("""
            import { NavComponent } from './nav/nav.component';
            import { NavService } from './nav/nav.service';
            @NgModule({
                imports: []
            })
export class Foo {}
        """)

        when:
        boolean success = editor.addDependency(file, model)

        then:
        file.text == """
            import { NavComponent } from './nav/nav.component';
            import { NavService } from './nav/nav.service';
import { BarModule } from './bar/bar.module';
            @NgModule({
                imports: [
    BarModule
]
            })
export class Foo {}
        """
        success
    }

    void "test addDependency with invalid module definition"() {
        given:
        file.write("""
            import { NavComponent } from './nav/nav.component';
            import { NavService } from './nav/nav.service';
            @NgModule({
                imports: ]
            })
export class Foo {}
        """)

        when:
        boolean success = editor.addDependency(file, model)

        then: "the file is unchanged"
        file.text == """
            import { NavComponent } from './nav/nav.component';
            import { NavService } from './nav/nav.service';
            @NgModule({
                imports: ]
            })
export class Foo {}
        """
        !success
    }


}
