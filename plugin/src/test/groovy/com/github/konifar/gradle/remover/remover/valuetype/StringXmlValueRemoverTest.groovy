package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class StringXmlValueRemoverTest extends Specification {

    private XmlValueRemover remover = new StringXmlValueRemover()

    def "type is valid"() {
        expect:
        remover.fileType == "string"
        remover.resourceName == "string"
        remover.tagName == "string"
        remover.type == SearchPattern.Type.DEFAULT
    }

    def "pattern matches"() {
        GString pattern = remover.createSearchPattern("app_name")

        expect:
        XmlValueRemover.isPatternMatched(fileText, pattern) == expected

        where:
        fileText              | expected
        "R.string.app_name"   | true
        "@string/app_name\""  | true
        "@string/app_name<"   | true
        "@string/app_name("   | true
        "@string/app_name)"   | true
        "@string/app_name}"   | true
        "@string/app_name"    | false
        "R.string.app"        | false
        "@string/app_name2\"" | false
        "@style/app_name"     | false
    }
}