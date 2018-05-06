package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class IntegerXmlValueRemoverTest extends Specification {

    private XmlValueRemover remover = new IntegerXmlValueRemover()

    def "type is valid"() {
        expect:
        remover.fileType == "integer"
        remover.resourceName == "integer"
        remover.tagName == "integer"
        remover.type == SearchPattern.Type.DEFAULT
    }

    def "pattern matches"() {
        GString pattern = remover.createSearchPattern("max_length")

        expect:
        XmlValueRemover.isPatternMatched(fileText, pattern) == expected

        where:
        fileText                 | expected
        "R.integer.max_length"   | true
        "@integer/max_length\""  | true
        "@integer/max_length<"   | true
        "R.integer.max"          | false
        "@integer/max_length2\"" | false
        "@integer/max_length"    | false
    }
}