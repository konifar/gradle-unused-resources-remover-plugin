package com.github.konifar.gradle.remover.remover

import com.github.konifar.gradle.remover.remover.StringXmlValueRemover;
import spock.lang.Specification;

class StringXmlValueRemoverTest extends Specification {

    def remover = new StringXmlValueRemover()

    def "type is string"() {
        expect:
        remover.fileType == "string"
    }

    def "pattern matches"() {
        def pattern = remover.createSearchPattern("app_name")
        def isMatched = false
        if (fileText =~ pattern) {
            isMatched = true
        }

        expect:
        isMatched == expected

        where:
        fileText              | expected
        "R.string.app_name"   | true
        "@string/app_name\""  | true
        "R.string.app"        | false
        "@string/app_name2\"" | false
        "@style/app_name"     | false
        // "R.string.app_name2"   | false
    }
}