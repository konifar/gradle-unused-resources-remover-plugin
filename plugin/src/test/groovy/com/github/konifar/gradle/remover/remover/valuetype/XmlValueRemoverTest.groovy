package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.filetype.FileRemover
import spock.lang.Specification

class XmlValueRemoverTest extends Specification {

    def "type is bool"() {
        XmlValueRemover remover = new XmlValueRemover("bool", "bool", "bool")
        GString pattern = remover.createSearchPattern("pref_value")
        boolean isMatched = FileRemover.isPatternMatched(fileText, pattern)

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