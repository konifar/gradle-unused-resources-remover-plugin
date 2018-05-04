package com.github.konifar.gradle.remover.file

class DrawableFileRemover extends FileRemover {

    @Override
    String getType() {
        return "drawable"
    }

    @Override
    GString createPattern(File file) {
        String fileName = extractFileName(file) - ".9" // Considered 9patch
        def pattern = /(${type}\/${fileName})|(R\.${type}\.${fileName})/
        return pattern
    }

}