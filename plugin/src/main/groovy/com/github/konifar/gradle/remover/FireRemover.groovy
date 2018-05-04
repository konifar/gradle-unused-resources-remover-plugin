package com.github.konifar.gradle.remover

import org.gradle.api.Project

class FileRemover {

    def type = "layout"

    def remove(Project project) {
        println "[${type}] Start checking =================="

        // Check each modules
        List<String> moduleSrcDirs = project.rootProject.allprojects
                .findAll { it.name != project.rootProject.name }
                .collect { "${project.rootProject.projectDir.path}/${it.name}/src" }

        moduleSrcDirs.each {
            println "[${type}]   Checking ${it}"

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
                    println "[${type}]     Complete to delete files."
                } else {
                    println "[${type}]     No unused files."
                }
            }
        }
    }

    def createPattern(File file) {
        String fileName = file.name.take(file.name.lastIndexOf('.'))

        //        // Considered 9patch
//        if (type.startsWith("drawable")) {
//            fileName = fileName - ".9"
//        }

        def pattern = /(${type}\/${fileName})|(R\.${type}\.${fileName})/

//        // Considered data binding
//        if (type.startsWith("layout")) {
//            pattern = /(${type}\/${fileName})|(R\.${type}\.${fileName})|(${toCamelCase(fileName, true)}Binding)/
//        }

        return pattern
    }

    def removeFileIfNeed(File file, List<String> moduleSrcDirs) {
        def pattern = createPattern(file)

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
            println("[${type}]       Remove ${file.name}")
            file.delete()
            return true
        } else {
            return false
        }
    }

}