package com.github.konifar.gradle.remover.remover.xmlvaluetype

class FloatXmlValueRemover extends com.github.konifar.gradle.remover.remover.xmlvaluetype.AbstractXmlValueRemover {

    @Override
    String getFileType() {
        return "float"
    }

    @Override
    String getResourceName() {
        return "dimen"
    }

    @Override
    String getTagName() {
        return "item"
    }
}