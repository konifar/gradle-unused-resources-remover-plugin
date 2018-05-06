package com.github.konifar.gradle.remover.remover

import com.github.konifar.gradle.remover.remover.filetype.FileRemover
import com.github.konifar.gradle.remover.remover.valuetype.XmlValueRemover

class UnusedResourcesRemoverExtension {

    static final String NAME = "unusedResourcesRemover"

    List<AbstractRemover> extraRemovers = []

    List<String> excludeFileNames = []

    AbstractRemover createFileRemover(String fileType, String resourceName, SearchPattern.Type type = SearchPattern.Type.DEFAULT) {
        return new FileRemover(fileType, resourceName, type)
    }

    AbstractRemover createXmlValueRemover(String fileType, String resourceName, String tagName, SearchPattern.Type type = SearchPattern.Type.DEFAULT) {
        return new XmlValueRemover(fileType, resourceName, tagName, type)
    }

}