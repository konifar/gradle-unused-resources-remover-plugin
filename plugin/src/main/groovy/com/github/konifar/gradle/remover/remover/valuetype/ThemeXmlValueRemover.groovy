package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.SearchPattern

class ThemeXmlValueRemover extends XmlValueRemover {

    ThemeXmlValueRemover() {
        // theme is actually style
        super("theme", "style", "style")
    }

    @Override
    GString createSearchPattern(String attrName) {
        return SearchPattern.create(resourceName, attrName, SearchPattern.Type.STYLE)
    }

}