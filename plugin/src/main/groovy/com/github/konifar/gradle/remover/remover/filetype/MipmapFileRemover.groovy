package com.github.konifar.gradle.remover.remover.filetype

import com.github.konifar.gradle.remover.remover.SearchPattern

class MipmapFileRemover extends FileRemover {

    MipmapFileRemover() {
        super("mipmap", "mipmap", SearchPattern.Type.DRAWABLE)
    }

}