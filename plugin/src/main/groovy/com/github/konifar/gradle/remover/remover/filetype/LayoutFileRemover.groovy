package com.github.konifar.gradle.remover.remover.filetype

class LayoutFileRemover extends AbstractFileRemover {

    @Override
    String getFileType() {
        return "layout"
    }

    @Override
    GString createSearchPattern(String fileName) {
        // Considered data binding
        def pattern = /(@${resourceName}\/${fileName}")|(@${resourceName}\/${fileName}<)|(R\.${resourceName}\.${fileName})|(${toCamelCase(fileName, true)}Binding)/
        return pattern
    }

    private static String toCamelCase(String text, boolean capitalized = false) {
        text = text.replaceAll("(_)([A-Za-z0-9])", { Object[] it -> it[2].toUpperCase() })
        return capitalized ? text.capitalize() : text
    }

}