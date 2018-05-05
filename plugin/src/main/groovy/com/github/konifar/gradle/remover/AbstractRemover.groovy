package com.github.konifar.gradle.remover

import org.gradle.api.Project

abstract class AbstractRemover {

    abstract String getFileType()

    abstract def removeEach(File resDirFile, List<String> moduleSrcDirs)

    String getResourceName() {
        return getFileType()
    }

    /**
     * Text grep pattern
     * @param attrName
     * @return
     */
    GString createPattern(String attrName) {
        def pattern = /(@${resourceName}\/${attrName}")|(R\.${resourceName}.${attrName})/
        return pattern
    }

    def remove(Project project) {
        println "[${fileType}] ================== Start ${fileType} checking =================="

        // Check each modules
        List<String> moduleSrcDirs = project.rootProject.allprojects
                .findAll { it.name != project.rootProject.name }
                .collect { "${project.rootProject.projectDir.path}/${it.name}/src" }

        moduleSrcDirs.each {
            String moduleSrcName = it - "${project.rootProject.projectDir.path}/"
            println "[${fileType}]   ${moduleSrcName}"

            File resDirFile = new File("${it}/main/res")
            if (resDirFile.exists()) {
                removeEach(resDirFile, moduleSrcDirs)
            }
        }
    }

}