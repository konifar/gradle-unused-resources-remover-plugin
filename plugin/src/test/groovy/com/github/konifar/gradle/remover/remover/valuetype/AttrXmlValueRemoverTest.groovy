package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class AttrXmlValueRemoverTest extends Specification {

    private XmlValueRemover remover = new AttrXmlValueRemover()

    def "type is valid"() {
        expect:
        remover.fileType == "attr"
        remover.resourceName == "styleable"
        remover.tagName == "declare-styleable"
        remover.type == SearchPattern.Type.DEFAULT
    }

    def "pattern matches"() {
        GString pattern = remover.createSearchPattern("CustomView")

        expect:
        XmlValueRemover.isPatternMatched(fileText, pattern) == expected

        where:
        fileText                 | expected
        "R.styleable.CustomView" | true
        "R.style.CustomView"     | false
    }

}