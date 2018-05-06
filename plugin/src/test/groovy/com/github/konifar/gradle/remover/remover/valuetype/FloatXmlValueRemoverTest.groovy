package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class FloatXmlValueRemoverTest extends Specification {

    private XmlValueRemover remover = new FloatXmlValueRemover()

    def "type is valid"() {
        expect:
        remover.fileType == "float"
        remover.resourceName == "dimen"
        remover.tagName == "item"
        remover.type == SearchPattern.Type.DEFAULT
    }

    def "pattern matches"() {
        GString pattern = remover.createSearchPattern("size_ratio")

        expect:
        XmlValueRemover.isPatternMatched(fileText, pattern) == expected

        where:
        fileText               | expected
        "R.dimen.size_ratio"   | true
        "@dimen/size_ratio\""  | true
        "@dimen/size_ratio<"   | true
        "R.dimen.size"         | false
        "@dimen/size_ratio2\"" | false
        "@style/size_ratio"    | false
    }

}