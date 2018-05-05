package com.github.konifar.gradle.remover.tag;

import spock.lang.Specification;

class DimenXmlTagRemoverTest extends Specification {

    def remover = new DimenXmlTagRemover()

    def "type is dimen"() {
        expect:
        remover.type == "dimen"
    }

    def "pattern matches"() {
        def pattern = remover.createPattern("text_medium")
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
        // "R.string.app_name2"   | false
    }
}