package com.github.konifar.gradle.remover.tag;

import spock.lang.Specification;

class StyleXmlTagRemoverTest extends Specification {

    def remover = new StyleXmlTagRemover()

    def "type is style"() {
        expect:
        remover.type == "style"
    }

    def "pattern matches"() {
        def pattern = remover.createPattern("TitleTextAppearance")
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
        "parent=\"TitleTextAppearance\"" | true
        "@style/TitleTextAppearance."    | true
        "R.style.TitleTextAppear"        | false
        "@style/TitleTextAppearance2\""  | false
        "@theme/TitleTextAppearance"     | false
        // "R.style.TitleTextAppearance2"   | false
    }
}