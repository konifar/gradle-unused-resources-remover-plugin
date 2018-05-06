package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class DimenXmlValueRemoverTest extends Specification {

    private XmlValueRemover remover = new DimenXmlValueRemover()

    def "type is valid"() {
        expect:
        remover.fileType == "dimen"
        remover.resourceName == "dimen"
        remover.tagName == "dimen"
        remover.type == SearchPattern.Type.DEFAULT
    }

    def "pattern matches"() {
        GString pattern = remover.createSearchPattern("text_medium")

        expect:
        XmlValueRemover.isPatternMatched(fileText, pattern) == expected

        where:
        fileText                | expected
        "R.dimen.text_medium"   | true
        "@dimen/text_medium\""  | true
        "@dimen/text_medium<"   | true
        "R.dimen.text"          | false
        "@dimen/text_medium2\"" | false
        "@style/text_medium"    | false
    }
}