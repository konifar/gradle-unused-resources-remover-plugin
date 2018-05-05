package com.github.konifar.gradle.remover.remover.valuetype

class FloatXmlValueRemover extends AbstractXmlValueRemover {

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