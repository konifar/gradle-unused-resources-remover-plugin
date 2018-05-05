package com.github.konifar.gradle.remover.file

import com.github.konifar.gradle.remover.AbstractRemover
import com.github.konifar.gradle.remover.Logger

abstract class AbstractFileRemover extends AbstractRemover {

    @Override
    def removeEach(File resDirFile, List<String> moduleSrcDirs) {
        def checkResult = false
        resDirFile.eachDirRecurse { dir ->
            if (dir =~ /\/${fileType}.*/) {
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

    private def removeFileIfNeed(File file, List<String> moduleSrcDirs) {
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
            Logger.printlnGreen("[${fileType}]       Remove ${file.name}")
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