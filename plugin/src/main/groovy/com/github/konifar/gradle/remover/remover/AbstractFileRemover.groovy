package com.github.konifar.gradle.remover.remover

import com.github.konifar.gradle.remover.remover.util.ColoredLogger
import com.github.konifar.gradle.remover.remover.util.DirectoryMatcher

abstract class AbstractFileRemover extends AbstractRemover {

    @Override
    def removeEach(File resDirFile, List<String> moduleSrcDirs) {
        def checkResult = false
        resDirFile.eachDirRecurse { dir ->
            if (DirectoryMatcher(dir.path, fileType)) {
                if (dir.isDirectory()) {
                    dir.eachFile { f ->
                        checkResult |= removeFileIfNeed(f, moduleSrcDirs)
                    }
                }
            }
        }

        if (checkResult) {
            println "[${fileType}]     Complete to remove files."
        } else {
            println "[${fileType}]     No unused files."
        }
    }

    def removeFileIfNeed(File file, List<String> moduleSrcDirs) {
        def pattern = createSearchPattern(extractFileName(file))

        def isMatched = false

        moduleSrcDirs.forEach {
            File srcDirFile = new File(it)
            if (srcDirFile.exists()) {
                srcDirFile.eachDirRecurse { dir ->
                    dir.eachFileMatch(~/(.*\.xml)|(.*\.kt)|(.*\.java)/) { f ->
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
            ColoredLogger.printlnGreen("[${fileType}]       Remove ${file.name}")
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