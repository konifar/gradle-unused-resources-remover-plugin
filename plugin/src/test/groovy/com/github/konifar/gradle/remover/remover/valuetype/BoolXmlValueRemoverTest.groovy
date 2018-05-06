package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class BoolXmlValueRemoverTest extends Specification {

    private XmlValueRemover remover = new BoolXmlValueRemover()

    def "type is valid"() {
        expect:
        remover.fileType == "bool"
        remover.resourceName == "bool"
        remover.tagName == "bool"
        remover.type == SearchPattern.Type.DEFAULT
    }

    def "pattern matches"() {
        GString pattern = remover.createSearchPattern("pref_value")

        expect:
        XmlValueRemover.isPatternMatched(fileText, pattern) == expected

        where:
        fileText              | expected
        "R.bool.pref_value"   | true
        "@bool/pref_value\""  | true
        "@bool/pref_value<"   | true
        "R.bool.pref"         | false
        "@bool/pref_value2\"" | false
        "@bool/pref_value"    | false
    }
}