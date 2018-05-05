package com.github.konifar.gradle.remover.remover

import spock.lang.Specification

class DimenXmlValueRemoverTest extends Specification {

    def remover = new DimenXmlValueRemover()

    def "type is dimen"() {
        expect:
        remover.fileType == "dimen"
    }

    def "pattern matches"() {
        def pattern = remover.createSearchPattern("text_medium")
        def isMatched = false
        if (fileText =~ pattern) {
            isMatched = true
        }

        expect:
        isMatched == expected

        where:
        fileText                | expected
        "R.dimen.text_medium"   | true
        "@dimen/text_medium\""  | true
        "R.dimen.text"          | false
        "@dimen/text_medium2\"" | false
        "@style/text_medium"    | false
    }
}