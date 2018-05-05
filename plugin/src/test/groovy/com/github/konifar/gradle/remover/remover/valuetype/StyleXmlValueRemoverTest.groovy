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

    def "convert name to camel case with underscore"() {
        expect:
        remover.toCamelCaseWithUnderscore(tagName) == expected

        where:
        tagName               | expected
        "TextTitle"           | "TextTitle"
        "TextTitle.long"      | "TextTitle_Long"
        "TextTitle.Long"      | "TextTitle_Long"
        "textTitle.long"      | "textTitle_Long"
        "text_title.long"     | "text_title_Long"
        "TextTitle.Long.Gray" | "TextTitle_Long_Gray"
    }
}