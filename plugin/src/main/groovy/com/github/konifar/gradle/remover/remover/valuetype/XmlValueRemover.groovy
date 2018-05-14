package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.AbstractRemover
import com.github.konifar.gradle.remover.remover.SearchPattern
import com.github.konifar.gradle.remover.util.ColoredLogger
import com.github.konifar.gradle.remover.util.DirectoryMatcher
import org.jdom2.*
import org.jdom2.input.SAXBuilder
import org.jdom2.output.EscapeStrategy
import org.jdom2.output.Format
import org.jdom2.output.LineSeparator
import org.jdom2.output.XMLOutputter

class XmlValueRemover extends AbstractRemover {

    /**
     * Tag name to extract value from xml like <`dimen` name="width">, <`string` name="app_name">
     */
    final String tagName

    private static final EscapeStrategy ESCAPE_STRATEGY = new EscapeStrategy() {
        @Override
        boolean shouldEscape(char ch) {
            // To support
            return Verifier.isHighSurrogate(ch) || 60 == ch >>> 10
        }
    }

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
        if (isMatchedExcludeNames(file.path)) {
            ColoredLogger.logYellow "[${fileType}]   Ignore checking ${file.name}"
            return
        }

        def isFileChanged = false

        Document doc = new SAXBuilder().build(file)
        Iterator<Content> iterator = doc.getRootElement().content.iterator()

        def isAfterRemoved = false

        while (iterator.hasNext()) {
            Content content = iterator.next()

            // Remove line break after element is removed
            if (isAfterRemoved && content?.getCType() == Content.CType.Text) {
                Text text = content as Text
                if (text.text.contains("\n")) {
                    if (!dryRun) {
                        iterator.remove()
                    }
                }
                isAfterRemoved = false

            } else if (content?.getCType() == Content.CType.Element) {
                Element element = content as Element
                if (element.name == tagName) {
                    Attribute attr = element.getAttribute("name")

                    if (attr != null) {
                        Attribute overrideAttr = element.getAttribute("override", Namespace.getNamespace("tools", "http://schemas.android.com/tools"))

                        if (overrideAttr?.value != "true") {
                            def isMatched = checkTargetTextMatches(attr.value)

                            if (!isMatched) {
                                ColoredLogger.logGreen("[${fileType}]   Remove ${attr.value} in ${file.name}")
                                if (!dryRun) {
                                    iterator.remove()
                                }
                                isAfterRemoved = true
                                isFileChanged = true
                            }

                        } else {
                            ColoredLogger.logYellow("[${fileType}]   Skip checking ${attr.value} in ${file.name}")
                        }
                    }
                }
            }
        }

        if (isFileChanged) {
            if (!dryRun) {
                saveFile(doc, file)
                removeFileIfNeed(file)
            }
        } else {
            ColoredLogger.log "[${fileType}]   No unused tags in ${file.name}"
        }
    }

    private static void saveFile(Document doc, File file) {
        def stringWriter = new StringWriter()

        new XMLOutputter().with {
            format = Format.getRawFormat()
            format.setLineSeparator(LineSeparator.SYSTEM)
            format.setTextMode(Format.TextMode.PRESERVE)
            format.setEncoding("utf-8")
            format.setEscapeStrategy(ESCAPE_STRATEGY)
            output(doc, stringWriter)
//            output(doc, new FileWriter(file))
//            output(doc, System.out)
        }

        // TODO This is a temporary fix to remove extra spaces in last </resources>.
        file.write(stringWriter.toString()?.replaceFirst(/\n\s+<\/resources>/, "\n</resources>"))
    }

    private void removeFileIfNeed(File file) {
        Document doc = new SAXBuilder().build(file)
        if (doc.getRootElement().getChildren(tagName).size() == 0) {
            ColoredLogger.logGreen "[${fileType}]   Remove ${file.name}."
            file.delete()
        }
    }

}