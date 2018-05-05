package com.github.konifar.gradle.remover.remover

import spock.lang.Specification

class ColorFileRemoverTest extends Specification {

    def remover = new com.github.konifar.gradle.remover.remover.ColorFileRemover()

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
        "R.color.primary)"  | true
        "@color/primary\""  | true
        "@color/primary2"   | false
        "R.color.secondary" | false
        "@style/primary\""  | false
    }
}