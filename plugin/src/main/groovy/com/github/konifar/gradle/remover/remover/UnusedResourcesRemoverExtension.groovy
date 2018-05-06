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

    /**
     * @param fileType is a prefix of the file name like `font`, `text_appearance`
     * @param resourceName is a name to check its existence like @`string`/app_name, $.`string`/app_name
     * @param tagName is a name to extract value from xml like <`string` name="app_name">, <`dimen` name="width">
     * @param type is search regex pattern type. Ex) themes.xml should specified to Type.STYLE because it is almost same usage with styles.xml
     * @return
     */
    AbstractRemover createXmlValueRemover(String fileType, String resourceName, String tagName, String type = null) {
        return new XmlValueRemover(fileType, resourceName, tagName, SearchPattern.Type.from(type))
    }

}