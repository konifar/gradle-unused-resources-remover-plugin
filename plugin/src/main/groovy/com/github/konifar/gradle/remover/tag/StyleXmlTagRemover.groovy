package com.github.konifar.gradle.remover.tag

class StyleXmlTagRemover extends XmlTagRemover {

    @Override
    String getType() {
        return "style"
    }

    @Override
    GString createPattern(String attrName) {
        // Considered style override
        def pattern = /(${type}\/${attrName}")|(R\.${type}\.${attrName})|(${attrName}\.)|(parent="${attrName}")/
        return pattern
    }

}