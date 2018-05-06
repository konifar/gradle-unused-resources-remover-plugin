package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.SearchPattern

class StyleXmlValueRemover extends XmlValueRemover {

    StyleXmlValueRemover() {
        super("style", "style", "style")
    }

    @Override
    GString createSearchPattern(String attrName) {
        return SearchPattern.create(resourceName, attrName, SearchPattern.Type.STYLE)
    }

}