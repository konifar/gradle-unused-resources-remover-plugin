package com.github.konifar.gradle.remover.remover

class StyleXmlValueRemover extends AbstractXmlValueRemover {

    @Override
    String getFileType() {
        return "style"
    }

    @Override
    GString createSearchPattern(String attrName) {
        // Considered style override
        def pattern = /(@${resourceName}\/${attrName}")|(R\.${resourceName}\.${toCamelCaseWithUnderscore(attrName)})|(${attrName}\.)|(parent="${attrName}")/
        return pattern
    }

    static String toCamelCaseWithUnderscore(String name) {
        return name.replaceAll("(\\.)([A-Za-z0-9])", { Object[] it -> "_${it[2].toUpperCase()}" })
    }

}