package com.github.konifar.gradle.remover.remover

import spock.lang.Specification

class IntegerXmlValueRemoverTest extends Specification {

    def remover = new IntegerXmlValueRemover()

    def "type is integer"() {
        expect:
        remover.fileType == "integer"
    }

    def "pattern matches"() {
        def pattern = remover.createSearchPattern("max_length")
        def isMatched = false
        if (fileText =~ pattern) {
            isMatched = true
        }

        expect:
        isMatched == expected

        where:
        fileText                 | expected
        "R.integer.max_length"   | true
        "@integer/max_length\""  | true
        "R.integer.max"          | false
        "@integer/max_length2\"" | false
        "@integer/max_length"    | false
    }
}