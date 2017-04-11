package angular.scaffolding

import grails.dev.commands.GrailsApplicationCommand
import org.grails.io.support.Resource
import org.grails.io.support.SpringIOUtils

class NgInstallTemplates implements GrailsApplicationCommand {

    boolean skipBootstrap = true

    @Override
    boolean handle() {
        try {
            templates("angular/**/*").each { Resource r ->
                String path = r.URL.toString().replaceAll(/^.*?META-INF/, "src/main")
                if (path.endsWith('/')) {
                    mkdir(path)
                } else {
                    File to = new File(path)
                    SpringIOUtils.copy(r, to)
                    println("Copied ${r.filename} to location ${to.canonicalPath}")
                }
            }
        } catch (e) {
            println e
        }
    }

}
