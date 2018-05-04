package com.github.konifar.gradle.remover

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jdom2.Attribute
import org.jdom2.Document
import org.jdom2.Element
import org.jdom2.input.SAXBuilder
import org.jdom2.output.Format
import org.jdom2.output.LineSeparator
import org.jdom2.output.XMLOutputter

class UnusedResourcesRemoverPlugin implements Plugin<Project> {

    static String toCamelCase(String text, boolean capitalized = false) {
        text = text.replaceAll("(_)([A-Za-z0-9])", { Object[] it -> it[2].toUpperCase() })
        return capitalized ? text.capitalize() : text
    }

    static def deleteEachTagIfNeed(String type, File file, File srcDirFile) {
        def baseName = file.name
        def tagName = type

        def isChanged = false

        Document doc = new SAXBuilder().build(file)
        Element root = doc.getRootElement()
        Iterator<Element> iterator = root.getChildren(tagName).iterator()

        while (iterator.hasNext()) {
            def isMatched = false

            Element element = iterator.next()
            Attribute attr = element.getAttribute("name")

            if (attr != null && srcDirFile.exists()) {
                def attrName = attr.value

                srcDirFile.eachDirRecurse { dir ->
                    dir.eachFileMatch(~/(.*\.xml)|(.*\.kt)|(.*\.java)/) { f ->
                        def fileText = f.text.replaceAll('\n', '').replaceAll(' ', '')

                        def pattern = /(${type}\/${attrName})|(R\.${type}.${attrName})/

                        // Considered style override
                        if (type.startsWith("style")) {
                            pattern = /(${type}\/${attrName})|(R\.${type}\.${attrName})|(${attrName}\.)|(parent="${attrName}")/
                        }

                        if (fileText =~ pattern) {
                            isMatched = true
                            return true
                        }
                    }
                }
            }

            if (!isMatched) {
                println("[${type}] Delete ${attr.value} in ${baseName}.")
                iterator.remove()
                isChanged = true
            }
        }

        if (isChanged) {
            new XMLOutputter().with {
                format = Format.getRawFormat()
                format.setLineSeparator(LineSeparator.NONE)
                format.setOmitEncoding(true)
                format.setOmitDeclaration(true)
                output(doc, new FileWriter(file))
                // output(doc, System.out)
            }
        } else {
            println "[${type}] No unused tags in ${baseName}."
        }

        doc = new SAXBuilder().build(file)
        if (doc.getRootElement().getChildren(tagName).size() == 0) {
            println "[${type}] Delete ${baseName}."
            file.delete()
        }
    }

    static def deleteTag(String type, File resDirFile, File srcDirFile) {
        if (resDirFile.exists()) {
            resDirFile.eachDirRecurse { dir ->
                if (dir =~ /.*\/values.*/) {
                    dir.eachFileMatch(~/.*${type}.*/) { file ->
                        deleteEachTagIfNeed(type, file, srcDirFile)
                    }
                }
            }
        }
    }

    static def deleteEachFileIfNeed(String type, File file, File srcDirFile) {
        def baseName = file.name
        def fileName = baseName.take(baseName.lastIndexOf('.'))

        // Considered 9patch
        if (type.startsWith("drawable")) {
            fileName = fileName - ".9"
        }

        def isMatched = false

        if (srcDirFile.exists()) {
            srcDirFile.eachDirRecurse { dir ->
                dir.eachFileMatch(~/(.*\.xml)|(.*\.kt)|(.*\.java)/) { f ->
                    def fileText = f.text.replaceAll('\n', '').replaceAll(' ', '')

                    def pattern = /(${type}\/${fileName})|(R\.${type}\.${fileName})/

                    // Considered data binding
                    if (type.startsWith("layout")) {
                        pattern = /(${type}\/${fileName})|(R\.${type}\.${fileName})|(${toCamelCase(fileName, true)}Binding)/
                    }

                    if (fileText =~ pattern) {
                        isMatched = true
                        return true
                    }
                }
            }
        }

        if (!isMatched) {
            println("[${type}] Delete ${baseName}")
            file.delete()
            return true
        } else {
            return false
        }
    }

    static def deleteFile(String type, File resDirFile, File srcDirFile) {
        if (resDirFile.exists()) {
            def checkResult = false
            resDirFile.eachDirRecurse { dir ->
                if (dir =~ /.*\/${type}.*/) {
                    dir.eachFile { file ->
                        checkResult |= deleteEachFileIfNeed(type, file, srcDirFile)
                    }
                }
            }

            if (checkResult) {
                println "[${type}] Complete to delete files."
            } else {
                println "[${type}] No unused files."
            }
        }
    }

    @Override
    void apply(Project project) {
        project.rootProject.allprojects.each { p ->
            if (p.name != project.rootProject.name) {
                println "================== Checking ${p.name} =================="

                def moduleSrcDir = "${project.rootProject.projectDir.path}/${p.name}/src"
                def srcDirFile = new File(moduleSrcDir)
                def resDirFile = new File("${moduleSrcDir}/main/res")

                [
                        "layout",
                        "drawable",
                        "mipmap",
                        "anim",
                        "animator",
                        "color",
                        "menu"
                ].forEach {
                    deleteFile(it, resDirFile, srcDirFile)
                }

                [
                        "style",
                        "string",
                        "dimen"
                ].forEach {
                    deleteTag(it, resDirFile, srcDirFile)
                }

                // TODO Support theme
                // TODO Support attr
            }
        }
    }

}