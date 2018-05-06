package com.github.konifar.gradle.remover.remover.filetype

import com.github.konifar.gradle.remover.remover.SearchPattern

class LayoutFileRemover extends FileRemover {

    LayoutFileRemover() {
        super("layout", "layout", SearchPattern.Type.LAYOUT)
    }

}