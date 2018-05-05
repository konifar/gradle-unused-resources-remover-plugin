package com.github.konifar.gradle.remover.file

import spock.lang.Specification

class LayoutFileRemoverTest extends Specification {

    def remover = new LayoutFileRemover()

    def "type is layout"() {
        expect:
        remover.fileType == "layout"
    }

    def "pattern matches"() {
        def pattern = remover.createSearchPattern("activity_main")
        def isMatched = false
        if (fileText =~ pattern) {
            isMatched = true
        }

        expect:
        isMatched == expected

        where:
        fileText                   | expected
        "R.layout.activity_main"   | true
        "@layout/activity_main\""  | true
        "ActivityMainBinding"      | true
        "R.layout.fragment_main"   | false
        "@layout/activity_main2\"" | false
        "@menu/activity_main"      | false
        // "ActivityMainBinding2"   | false
        // "R.layout.activity_main2"   | false
    }
}