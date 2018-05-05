package com.github.konifar.gradle.remover.file

class DrawableFileRemover extends AbstractFileRemover {

    @Override
    String getFileType() {
        return "drawable"
    }

    @Override
    GString createPattern(String fileName) {
        fileName -= ".9" // Considered 9patch
        return super.createPattern(fileName)
    }

}