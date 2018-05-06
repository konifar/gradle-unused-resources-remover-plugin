package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class StyleXmlValueRemoverTest extends Specification {

    private XmlValueRemover remover = new StyleXmlValueRemover()

    def "type is valid"() {
        expect:
        remover.fileType == "style"
        remover.resourceName == "style"
        remover.tagName == "style"
        remover.type == SearchPattern.Type.STYLE
    }

    def "pattern matches"() {
        GString pattern = remover.createSearchPattern("TitleTextAppearance")

        expect:
        XmlValueRemover.isPatternMatched(fileText, pattern) == expected

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