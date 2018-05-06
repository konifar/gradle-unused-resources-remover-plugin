package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.SearchPattern

class ThemeXmlValueRemover extends XmlValueRemover {

    ThemeXmlValueRemover() {
        // theme is actually style
        super("theme", "style", "style", SearchPattern.Type.STYLE)
    }

}