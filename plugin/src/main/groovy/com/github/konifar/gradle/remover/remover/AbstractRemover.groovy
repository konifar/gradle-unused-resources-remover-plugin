package com.github.konifar.gradle.remover.remover

import org.gradle.api.Project

abstract class AbstractRemover {

    abstract String getFileType()

    abstract def removeEach(File resDirFile, List<String> moduleSrcDirs)

    String getResourceName() {
        return getFileType()
    }

    /**
     * @param target is file name or attribute name
     * @return pattern string to grep src
     */
    GString createSearchPattern(String target) {
        def pattern = /(@${resourceName}\/${target}")|(R\.${resourceName}\.${target})/
        return pattern
    }

    def remove(Project project) {
        println "[${fileType}] ================== Start ${fileType} checking =================="

        // Check each modules
        List<String> moduleSrcDirs = project.rootProject.allprojects
                .findAll { it.name != project.rootProject.name }
                .collect { "${it.projectDir.path}/src" }

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