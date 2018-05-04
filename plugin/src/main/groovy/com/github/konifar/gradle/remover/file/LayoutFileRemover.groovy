package com.github.konifar.gradle.remover.file

class LayoutFileRemover extends FileRemover {

    @Override
    String getType() {
        return "layout"
    }

    @Override
    GString createPattern(File file) {
        String fileName = extractFileName(file)
        // Considered data binding
        def pattern = /(${type}\/${fileName})|(R\.${type}\.${fileName})|(${toCamelCase(fileName, true)}Binding)/
        return pattern
    }

    private static String toCamelCase(String text, boolean capitalized = false) {
        text = text.replaceAll("(_)([A-Za-z0-9])", { Object[] it -> it[2].toUpperCase() })
        return capitalized ? text.capitalize() : text
    }

}