package com.github.konifar.gradle.remover.remover

import spock.lang.Specification

class SearchPatternTest extends Specification {

    def "convert name to camel case with underscore"() {
        expect:
        SearchPattern.toCamelCaseWithUnderscore(tagName) == expected

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