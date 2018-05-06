package com.github.konifar.gradle.remover.remover.filetype

import com.github.konifar.gradle.remover.remover.SearchPattern

class DrawableFileRemover extends FileRemover {

    DrawableFileRemover() {
        super("drawable", "drawable")
    }

    @Override
    GString createSearchPattern(String fileName) {
        return SearchPattern.create(resourceName, fileName, SearchPattern.Type.DRAWABLE)
    }

}