package com.github.konifar.gradle.remover.remover

import com.github.konifar.gradle.remover.remover.filetype.FileRemover
import com.github.konifar.gradle.remover.remover.valuetype.XmlValueRemover

class UnusedResourcesRemoverExtension {

    static final String NAME = "unusedResourcesRemover"

    List<AbstractRemover> extraRemovers = []

    List<String> excludeNames = []

    boolean dryRun = false

    AbstractRemover createFileRemover(String fileType, String resourceName, String type = null) {
        return new FileRemover(fileType, resourceName, SearchPattern.Type.from(type))
    }

    AbstractRemover createXmlValueRemover(String fileType, String resourceName, String tagName, String type = null) {
        return new XmlValueRemover(fileType, resourceName, tagName, SearchPattern.Type.from(type))
    }

}