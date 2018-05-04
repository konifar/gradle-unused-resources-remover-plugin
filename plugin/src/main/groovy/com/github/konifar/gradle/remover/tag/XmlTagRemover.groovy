package com.github.konifar.gradle.remover.tag

import org.gradle.api.Project
import org.jdom2.Attribute
import org.jdom2.Document
import org.jdom2.Element
import org.jdom2.input.SAXBuilder
import org.jdom2.output.Format
import org.jdom2.output.LineSeparator
import org.jdom2.output.XMLOutputter

abstract class XmlTagRemover {

    /**
     * Resource file type
     */
    abstract String getType()

    String getTagName() {
        return getType()
    }

    /**
     * Text grep pattern
     * @param attrName
     * @return
     */
    GString createPattern(String attrName) {
        def pattern = /(${type}\/${attrName})|(R\.${type}.${attrName})/

        // Considered style override
        if (type.startsWith("style")) {
            pattern = /(${type}\/${attrName})|(R\.${type}\.${attrName})|(${attrName}\.)|(parent="${attrName}")/
        }

        return pattern
    }

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
                resDirFile.eachDirRecurse { dir ->
                    if (dir =~ /\/values.*/) {
                        dir.eachFileMatch(~/${type}.*/) { f ->
                            removeTagIfNeed(f, moduleSrcDirs)
                        }
                    }
                }
            }
        }
    }

    def removeTagIfNeed(File file, List<String> moduleSrcDirs) {
        def isFileChanged = false

        Document doc = new SAXBuilder().build(file)
        Element root = doc.getRootElement()
        Iterator<Element> iterator = root.getChildren(tagName).iterator()

        while (iterator.hasNext()) {
            def isMatched = false

            Element element = iterator.next()
            Attribute attr = element.getAttribute("name")

            moduleSrcDirs.forEach {
                File srcDirFile = new File(it)

                if (attr != null && srcDirFile.exists()) {
                    def pattern = createPattern(attr.value)

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
                println("[${type}]       Remove ${attr.value} in ${file.name}")
                iterator.remove()
                isFileChanged = true
            }
        }

        if (isFileChanged) {
            saveFile(doc, file)
        } else {
            println "[${type}]     No unused tags in ${file.name}"
        }

        removeFileIfNeed(file)
    }

    private static def saveFile(Document doc, File file) {
        new XMLOutputter().with {
            format = Format.getRawFormat()
            format.setLineSeparator(LineSeparator.NONE)
            format.setOmitEncoding(true)
            format.setOmitDeclaration(true)
            output(doc, new FileWriter(file))
            // output(doc, System.out)
        }
    }

    private def removeFileIfNeed(File file) {
        Document doc = new SAXBuilder().build(file)
        if (doc.getRootElement().getChildren(tagName).size() == 0) {
            println "[${type}]   remove ${file.name}."
            file.delete()
        }
    }

}