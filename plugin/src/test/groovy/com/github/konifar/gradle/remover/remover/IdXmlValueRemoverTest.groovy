package com.github.konifar.gradle.remover.remover

import spock.lang.Specification

class IdXmlValueRemoverTest extends Specification {

    def remover = new com.github.konifar.gradle.remover.remover.xmlvaluetype.IdXmlValueRemover()

    def "type is id"() {
        expect:
        remover.fileType == "id"
    }

    def "tag name is item"() {
        expect:
        remover.tagName == "item"
    }

    def "pattern matches"() {
        def pattern = remover.createSearchPattern("view_id")
        def isMatched = false
        if (fileText =~ pattern) {
            isMatched = true
        }

        expect:
        isMatched == expected

        where:
        fileText         | expected
        "R.id.view_id"   | true
        "@id/view_id\""  | true
        "R.id.view"      | false
        "@id/view_id2\"" | false
    }

}