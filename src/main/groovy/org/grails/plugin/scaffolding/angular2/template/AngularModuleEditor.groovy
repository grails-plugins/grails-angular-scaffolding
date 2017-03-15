package org.grails.plugin.scaffolding.angular2.template

import grails.codegen.model.Model

interface AngularModuleEditor {

    boolean addModuleImport(File module, Model model)

    boolean addImport(File module, Model model, String relativeDir)

    boolean addDependency(File module, Model model, String relativeDir)
}