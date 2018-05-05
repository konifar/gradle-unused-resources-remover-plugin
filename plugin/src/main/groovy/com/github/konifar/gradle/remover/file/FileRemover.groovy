package com.github.konifar.gradle.remover.file

import com.github.konifar.gradle.remover.Logger
import org.gradle.api.Project

abstract class FileRemover {

    /**
     * Resource file type
     */
    abstract String getType()

    /**
     * Text grep pattern
     * @param file
     * @return
     */
    GString createPattern(String fileName) {
        def pattern = /(@${type}\/${fileName}")|(R\.${type}\.${fileName})/
        return pattern
    }

    def remove(Project project) {
        println "[${type}] ================== Start ${type} checking =================="

        // Check each modules
        List<String> moduleSrcDirs = project.rootProject.allprojects
                .findAll { it.name != project.rootProject.name }
                .collect { "${project.rootProject.projectDir.path}/${it.name}/src" }

        moduleSrcDirs.each {
            String moduleSrcName = it - "${project.rootProject.projectDir.path}/"
            println "[${type}]   ${moduleSrcName}"

            File resDirFile = new File("${it}/main/res")
            if (resDirFile.exists()) {
                def checkResult = false
                resDirFile.eachDirRecurse { dir ->
                    if (dir =~ /\/${type}.*/) {
                        if (dir.isDirectory()) {
                            dir.eachFile { f ->
                                checkResult |= removeFileIfNeed(f, moduleSrcDirs)
                            }
                        }
                    }
                }

                if (checkResult) {
                    println "[${type}]     Complete to remove files."
                } else {
                    println "[${type}]     No unused files."
                }
            }
        }
    }

    def removeFileIfNeed(File file, List<String> moduleSrcDirs) {
        def pattern = createPattern(extractFileName(file))

        def isMatched = false

        moduleSrcDirs.forEach {
            File srcDirFile = new File(it)
            if (srcDirFile.exists()) {
                srcDirFile.eachDirRecurse { dir ->
                    dir.eachFileMatch(~/(.*\.xml)|(.*\.kt)|(.*\.java)/) { f ->
                        // println "[${type}]         ${dir.name}/${f.name}"

                        def fileText = f.text.replaceAll('\n', '').replaceAll(' ', '')
                        if (fileText =~ pattern) {
                            isMatched = true
                            return true
                        }
                    }
                }
            }
        }

        if (!isMatched) {
            Logger.printlnGreen("[${type}]       Remove ${file.name}")
            file.delete()
            return true
        } else {
            return false
        }
    }

    private static String extractFileName(File file) {
        return file.name.take(file.name.lastIndexOf('.'))
    }

}