package com.github.konifar.gradle.remover.remover.valuetype

class IdXmlValueRemover extends AbstractXmlValueRemover {

    @Override
    String getFileType() {
        return "id"
    }

    @Override
    String getTagName() {
        return "item"
    }
}