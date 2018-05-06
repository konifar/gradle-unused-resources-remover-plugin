package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class ColorXmlValueRemoverTest extends Specification {

    private XmlValueRemover remover = new ColorXmlValueRemover()

    def "type is valid"() {
        expect:
        remover.fileType == "color"
        remover.resourceName == "color"
        remover.tagName == "color"
        remover.type == SearchPattern.Type.DEFAULT
    }

    def "pattern matches"() {
        GString pattern = remover.createSearchPattern("primary")

        expect:
        XmlValueRemover.isPatternMatched(fileText, pattern) == expected

        where:
        fileText            | expected
        "R.color.primary"   | true
        "@color/primary\""  | true
        "@color/primary<"   | true
        "@color/primary:"   | true
        "@color/primary"    | false
        "R.color.secondary" | false
        "@color/primary2\"" | false
        "@style/primary"    | false
    }
}