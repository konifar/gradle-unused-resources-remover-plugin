package com.github.konifar.gradle.remover.remover

class DrawableFileRemover extends AbstractFileRemover {

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