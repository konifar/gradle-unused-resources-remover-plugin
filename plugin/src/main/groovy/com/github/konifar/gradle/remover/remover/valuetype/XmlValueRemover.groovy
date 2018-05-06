package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.AbstractRemover
import com.github.konifar.gradle.remover.remover.util.ColoredLogger
import com.github.konifar.gradle.remover.remover.util.DirectoryMatcher
import org.jdom2.Attribute
import org.jdom2.Document
import org.jdom2.Element
import org.jdom2.input.SAXBuilder
import org.jdom2.output.Format
import org.jdom2.output.LineSeparator
import org.jdom2.output.XMLOutputter

class XmlValueRemover extends AbstractRemover {

    /**
     * Tag name to extract value from xml like <`dimen` name="width">, <`string` name="app_name">
     */
    final String tagName

    XmlValueRemover(String fileType, String resourceName, String tagName) {
        super(fileType, resourceName)
        this.tagName = tagName
    }

    @Override
    def removeEach(File resDirFile, List<String> moduleSrcDirs) {
        resDirFile.eachDirRecurse { dir ->
            if (DirectoryMatcher.matchLast(dir.path, "values")) {
                dir.eachFileMatch(~/${fileType}.*/) { f ->
                    removeTagIfNeed(f, moduleSrcDirs)
                }
            }
        }
    }

    def removeTagIfNeed(File file, List<String> moduleSrcDirs) {
        def isFileChanged = false

        Document doc = new SAXBuilder().build(file)
        Iterator<Element> iterator = doc.getRootElement().getChildren(tagName).iterator()

        while (iterator.hasNext()) {
            Attribute attr = iterator.next().getAttribute("name")

            if (attr != null) {
                def isMatched = checkTargetTextMatches(attr.value, moduleSrcDirs)

                if (!isMatched) {
                    ColoredLogger.logGreen("[${fileType}]   Remove ${attr.value} in ${file.name}")
                    iterator.remove()
                    isFileChanged = true
                }
            }
        }

        if (isFileChanged) {
            saveFile(doc, file)
            removeFileIfNeed(file)
        } else {
            ColoredLogger.log "[${fileType}]   No unused tags in ${file.name}"
        }
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
            ColoredLogger.logGreen "[${fileType}]   Remove ${file.name}."
            file.delete()
        }
    }

}