package com.github.konifar.gradle.remover.remover

class FloatXmlValueRemover extends AbstractXmlValueRemover {

    @Override
    String getFileType() {
        return "float"
    }

    @Override
    String getResourceName() {
        return "dimen"
    }
}