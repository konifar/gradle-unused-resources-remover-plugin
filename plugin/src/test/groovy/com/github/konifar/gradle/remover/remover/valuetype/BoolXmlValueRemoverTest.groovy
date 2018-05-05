package com.github.konifar.gradle.remover.remover.valuetype

import spock.lang.Specification

class BoolXmlValueRemoverTest extends Specification {

    def remover = new BoolXmlValueRemover()

    def "type is bool"() {
        expect:
        remover.fileType == "bool"
    }

    def "pattern matches"() {
        def pattern = remover.createSearchPattern("pref_value")
        def isMatched = false
        if (fileText =~ pattern) {
            isMatched = true
        }

        expect:
        isMatched == expected

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