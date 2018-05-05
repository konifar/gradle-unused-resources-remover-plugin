package com.github.konifar.gradle.remover.remover.xmlvaluetype

class IdXmlValueRemover extends com.github.konifar.gradle.remover.remover.xmlvaluetype.AbstractXmlValueRemover {

    @Override
    String getFileType() {
        return "id"
    }

    @Override
    String getTagName() {
        return "item"
    }
}