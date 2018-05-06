package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class IdXmlValueRemoverTest extends Specification {

    private XmlValueRemover remover = new IdXmlValueRemover()

    def "type is valid"() {
        expect:
        remover.fileType == "id"
        remover.resourceName == "id"
        remover.tagName == "item"
        remover.type == SearchPattern.Type.DEFAULT
    }

    def "pattern matches"() {
        GString pattern = remover.createSearchPattern("view_id")

        expect:
        XmlValueRemover.isPatternMatched(fileText, pattern) == expected

        where:
        fileText         | expected
        "R.id.view_id"   | true
        "@id/view_id\""  | true
        "@id/view_id<"   | true
        "R.id.view"      | false
        "@id/view_id2\"" | false
    }

}