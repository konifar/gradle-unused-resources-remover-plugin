package com.github.konifar.gradle.remover.remover.filetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class LayoutFileRemoverTest extends Specification {

    FileRemover remover = new LayoutFileRemover()

    def "type is valid"() {
        expect:
        remover.fileType == "layout"
        remover.resourceName == "layout"
        remover.type == SearchPattern.Type.LAYOUT
    }

    def "matches pattern"() {
        GString pattern = remover.createSearchPattern("activity_main")

        expect:
        FileRemover.isPatternMatched(fileText, pattern) == expected

        where:
        fileText                   | expected
        "R.layout.activity_main"   | true
        "@layout/activity_main\""  | true
        "@layout/activity_main<"   | true
        "ActivityMainBinding"      | true
        "R.layout.fragment_main"   | false
        "@layout/activity_main2\"" | false
        "@menu/activity_main"      | false
    }

}