package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.SearchPattern

class StyleXmlValueRemover extends XmlValueRemover {

    StyleXmlValueRemover() {
        super("style", "style", "style", SearchPattern.Type.STYLE)
    }

}