package com.github.konifar.gradle.remover.remover.valuetype

class ThemeXmlValueRemover extends XmlValueRemover {

    ThemeXmlValueRemover() {
        // theme is actually style
        super("theme", "style", "style")
    }

    @Override
    GString createSearchPattern(String attrName) {
        // Considered style override
        def pattern = /(@${resourceName}\/${attrName}")|(@${resourceName}\/${attrName}<)|(R\.${resourceName}\.${toCamelCaseWithUnderscore(attrName)})|(${attrName}\.)|(parent="${attrName}")/
        println pattern
        return pattern
    }

    static String toCamelCaseWithUnderscore(String name) {
        return name.replaceAll("(\\.)([A-Za-z0-9])", { Object[] it -> "_${it[2].toUpperCase()}" })
    }

}