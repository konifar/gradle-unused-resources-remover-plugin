package com.github.konifar.gradle.remover.remover.valuetype

import spock.lang.Specification

class StyleXmlValueRemoverTest extends Specification {

    def remover = new StyleXmlValueRemover()

    def "type is style"() {
        expect:
        remover.fileType == "style"
    }

    def "pattern matches"() {
        def pattern = remover.createSearchPattern("TitleTextAppearance")
        def isMatched = false
        if (fileText =~ pattern) {
            isMatched = true
        }

        expect:
        isMatched == expected

        where:
        fileText                         | expected
        "R.style.TitleTextAppearance"    | true
        "@style/TitleTextAppearance\""   | true
        "@style/TitleTextAppearance<"    | true
        "parent=\"TitleTextAppearance\"" | true
        "@style/TitleTextAppearance."    | true
        "R.style.TitleTextAppear"        | false
        "@style/TitleTextAppearance2\""  | false
        "@theme/TitleTextAppearance"     | false
    }

}