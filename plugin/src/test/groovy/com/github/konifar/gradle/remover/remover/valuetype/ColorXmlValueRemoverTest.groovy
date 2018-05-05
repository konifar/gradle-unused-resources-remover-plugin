package com.github.konifar.gradle.remover.remover.valuetype

import spock.lang.Specification

class ColorXmlValueRemoverTest extends Specification {

    def remover = new ColorXmlValueRemover()

    def "type is color"() {
        expect:
        remover.fileType == "color"
    }

    def "pattern matches"() {
        def pattern = remover.createSearchPattern("primary")
        def isMatched = false
        if (fileText =~ pattern) {
            isMatched = true
        }

        expect:
        isMatched == expected

        where:
        fileText            | expected
        "R.color.primary"   | true
        "@color/primary\""  | true
        "@color/primary<"   | true
        "R.color.secondary" | false
        "@color/primary2\"" | false
        "@style/primary"    | false
    }
}