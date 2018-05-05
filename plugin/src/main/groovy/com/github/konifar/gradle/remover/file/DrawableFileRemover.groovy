package com.github.konifar.gradle.remover.file

class DrawableFileRemover extends FileRemover {

    @Override
    String getType() {
        return "drawable"
    }

    @Override
    GString createPattern(String fileName) {
        fileName -= ".9" // Considered 9patch
        return super.createPattern(fileName)
    }

}