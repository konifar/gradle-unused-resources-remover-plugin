package com.github.konifar.gradle.remover.tag

import com.github.konifar.gradle.remover.AbstractRemover
import com.github.konifar.gradle.remover.Logger
import org.jdom2.Attribute
import org.jdom2.Document
import org.jdom2.Element
import org.jdom2.input.SAXBuilder
import org.jdom2.output.Format
import org.jdom2.output.LineSeparator
import org.jdom2.output.XMLOutputter

abstract class AbstractXmlTagRemover extends AbstractRemover {

    @Override
    def removeEach(File resDirFile, List<String> moduleSrcDirs) {
        resDirFile.eachDirRecurse { dir ->
            if (dir =~ /\/values.*/) {
                dir.eachFileMatch(~/${fileType}.*/) { f ->
                    removeTagIfNeed(f, moduleSrcDirs)
                }
            }
        }
    }

    def removeTagIfNeed(File file, List<String> moduleSrcDirs) {
        def isFileChanged = false

        Document doc = new SAXBuilder().build(file)
        Element root = doc.getRootElement()
        Iterator<Element> iterator = root.getChildren(resourceName).iterator()

        while (iterator.hasNext()) {
            def isMatched = false

            Element element = iterator.next()
            Attribute attr = element.getAttribute("name")

            moduleSrcDirs.forEach {
                File srcDirFile = new File(it)

                if (attr != null && srcDirFile.exists()) {
                    def pattern = createSearchPattern(attr.value)

                    srcDirFile.eachDirRecurse { dir ->
                        dir.eachFileMatch(~/(.*\.xml)|(.*\.kt)|(.*\.java)/) { f ->
                            println "[${fileType}]         ${dir.name}/${f.name}"

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
                Logger.printlnGreen("[${fileType}]       Remove ${attr.value} in ${file.name}")
                iterator.remove()
                isFileChanged = true
            }
        }

        if (isFileChanged) {
            saveFile(doc, file)
        } else {
            println "[${fileType}]     No unused tags in ${file.name}"
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
        if (doc.getRootElement().getChildren(resourceName).size() == 0) {
            println "[${fileType}]   remove ${file.name}."
            file.delete()
        }
    }

}