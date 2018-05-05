package com.github.konifar.gradle.remover.file

import spock.lang.Specification

class ColorFileRemoverTest extends Specification {

    def remover = new ColorFileRemover()

    def "type is color"() {
        expect:
        remover.type == "color"
    }

    def "pattern matches"() {
        def pattern = remover.createPattern("primary")
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
        // "R.color.primary2"   | false
    }
}