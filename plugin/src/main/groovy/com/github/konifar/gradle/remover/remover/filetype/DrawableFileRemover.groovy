package com.github.konifar.gradle.remover.remover.filetype

class DrawableFileRemover extends com.github.konifar.gradle.remover.remover.filetype.AbstractFileRemover {

    @Override
    String getFileType() {
        return "drawable"
    }

    @Override
    GString createSearchPattern(String fileName) {
        fileName -= ".9" // Considered 9patch
        return super.createSearchPattern(fileName)
    }

}