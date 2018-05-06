package com.github.konifar.gradle.remover.remover

import com.github.konifar.gradle.remover.remover.util.ColoredLogger
import org.gradle.api.Project
import org.gradle.internal.impldep.com.google.common.annotations.VisibleForTesting

abstract class AbstractRemover {

    /**
     * directory/file name to find files like drawable, dimen, string
     */
    final String fileType

    /**
     * Resource name to check its existence like @`string`/app_name, $.`string`/app_name
     */
    final String resourceName

    /**
     * Search pattern
     * ex) theme should specified to Type.STYLE
     */
    final SearchPattern.Type type

    AbstractRemover(String fileType, String resourceName, SearchPattern.Type type) {
        this.fileType = fileType
        this.resourceName = resourceName
        this.type = type
    }

    abstract void removeEach(File resDirFile, List<String> moduleSrcDirs)

    /**
     * @param target is file name or attribute name
     * @return pattern string to grep src
     */
    @VisibleForTesting
    GString createSearchPattern(String target) {
        return SearchPattern.create(resourceName, target, type)
    }

    void remove(Project project) {
        ColoredLogger.log "[${fileType}] ======== Start ${fileType} checking ========"

        // Check each modules
        List<String> moduleSrcDirs = project.rootProject.allprojects
                .findAll { it.name != project.rootProject.name }
                .collect { "${it.projectDir.path}/src" }

        moduleSrcDirs.each {
            String moduleSrcName = it - "${project.rootProject.projectDir.path}/" - "/src"
            ColoredLogger.log "[${fileType}] ${moduleSrcName}"

            File resDirFile = new File("${it}/main/res")
            if (resDirFile.exists()) {
                removeEach(resDirFile, moduleSrcDirs)
            }
        }
    }

    @VisibleForTesting
    static boolean isPatternMatched(String fileText, GString pattern) {
        return fileText =~ pattern
    }

    boolean checkTargetTextMatches(String targetText, List<String> moduleSrcDirs) {
        def pattern = createSearchPattern(targetText)
        def isMatched = false

        moduleSrcDirs.forEach {
            File srcDirFile = new File(it)

            if (srcDirFile.exists()) {
                srcDirFile.eachDirRecurse { dir ->
                    dir.eachFileMatch(~/(.*\.xml)|(.*\.kt)|(.*\.java)/) { f ->
                        def fileText = f.text.replaceAll('\n', '').replaceAll(' ', '')
                        if (isPatternMatched(fileText, pattern)) {
                            isMatched = true
                            return true
                        }
                    }
                }
            }
        }

        return isMatched
    }

}