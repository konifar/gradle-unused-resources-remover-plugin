package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.AbstractRemover
import com.github.konifar.gradle.remover.remover.SearchPattern
import com.github.konifar.gradle.remover.util.ColoredLogger
import com.github.konifar.gradle.remover.util.DirectoryMatcher
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

    XmlValueRemover(String fileType, String resourceName, String tagName, SearchPattern.Type type = SearchPattern.Type.DEFAULT) {
        super(fileType, resourceName, type)
        this.tagName = tagName
    }

    @Override
    void removeEach(File resDirFile) {
        resDirFile.eachDirRecurse { dir ->
            if (DirectoryMatcher.matchLast(dir.path, "values")) {
                dir.eachFileMatch(~/${fileType}.*/) { f ->
                    removeTagIfNeed(f)
                }
            }
        }
    }

    void removeTagIfNeed(File file) {
        def isFileChanged = false

        Document doc = new SAXBuilder().build(file)
        Iterator<Element> iterator = doc.getRootElement().getChildren(tagName).iterator()

        while (iterator.hasNext()) {
            Attribute attr = iterator.next().getAttribute("name")

            if (attr != null) {
                def isMatched = checkTargetTextMatches(attr.value)

                if (!isMatched) {
                    ColoredLogger.logGreen("[${fileType}]   Remove ${attr.value} in ${file.name}")
                    if (!dryRun) {
                        iterator.remove()
                    }
                    isFileChanged = true
                }
            }
        }

        if (isFileChanged) {
            if (isMatchedExcludeNames(file.path)) {
                ColoredLogger.logYellow "[${fileType}]   Ignore to remove values in ${file.name}"
                return
            }

            if (!dryRun) {
                saveFile(doc, file)
                removeFileIfNeed(file)
            }
        } else {
            ColoredLogger.log "[${fileType}]   No unused tags in ${file.name}"
        }
    }

    private static void saveFile(Document doc, File file) {
        new XMLOutputter().with {
            format = Format.getRawFormat()
            format.setLineSeparator(LineSeparator.NONE)
            format.setOmitEncoding(true)
            format.setOmitDeclaration(true)
            output(doc, new FileWriter(file))
            // output(doc, System.out)
        }
    }

    private void removeFileIfNeed(File file) {
        Document doc = new SAXBuilder().build(file)
        if (doc.getRootElement().getChildren(tagName).size() == 0) {
            ColoredLogger.logGreen "[${fileType}]   Remove ${file.name}."
            file.delete()
        }
    }

}