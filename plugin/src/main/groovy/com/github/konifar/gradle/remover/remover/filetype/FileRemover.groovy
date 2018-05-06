package com.github.konifar.gradle.remover.remover.filetype

import com.github.konifar.gradle.remover.remover.AbstractRemover
import com.github.konifar.gradle.remover.remover.SearchPattern
import com.github.konifar.gradle.remover.util.ColoredLogger
import com.github.konifar.gradle.remover.util.DirectoryMatcher

class FileRemover extends AbstractRemover {

    FileRemover(String fileType, String resourceName, SearchPattern.Type type = SearchPattern.Type.DEFAULT) {
        super(fileType, resourceName, type)
    }

    @Override
    void removeEach(File resDirFile) {
        def checkResult = false
        resDirFile.eachDirRecurse { dir ->
            if (DirectoryMatcher.matchLast(dir.path, fileType)) {
                dir.eachFile { f ->
                    checkResult |= removeFileIfNeed(f)
                }
            }
        }

        if (checkResult) {
            ColoredLogger.log "[${fileType}]   Complete to remove files."
        } else {
            ColoredLogger.log "[${fileType}]   No unused ${fileType} files."
        }
    }

    boolean removeFileIfNeed(File file) {
        boolean isMatched = checkTargetTextMatches(extractFileName(file))

        if (!isMatched) {
            ColoredLogger.logGreen("[${fileType}]   Remove ${file.name}")
            if (!dryRun) {
                file.delete()
            }
            return true
        } else {
            return false
        }
    }

    private static String extractFileName(File file) {
        return file.name.take(file.name.lastIndexOf('.'))
    }

}