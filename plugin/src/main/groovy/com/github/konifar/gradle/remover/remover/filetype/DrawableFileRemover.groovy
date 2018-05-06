package com.github.konifar.gradle.remover.remover.filetype

class DrawableFileRemover extends FileRemover {

    DrawableFileRemover() {
        super("drawable", "drawable")
    }

    @Override
    GString createSearchPattern(String fileName) {
        fileName -= ".9" // Considered 9patch
        return super.createSearchPattern(fileName)
    }

}