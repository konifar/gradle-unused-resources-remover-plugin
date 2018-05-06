package com.github.konifar.gradle.remover.remover.filetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class ColorFileRemoverTest extends Specification {

    private FileRemover remover = new ColorFileRemover()

    def "type is valid"() {
        expect:
        remover.fileType == "color"
        remover.resourceName == "color"
        remover.type == SearchPattern.Type.DEFAULT
    }

    def "pattern matches"() {
        GString pattern = remover.createSearchPattern("primary")

        expect:
        FileRemover.isPatternMatched(fileText, pattern) == expected

        where:
        fileText            | expected
        "R.color.primary"   | true
        "R.color.primary)"  | true
        "@color/primary\""  | true
        "@color/primary<"   | true
        "@color/primary2"   | false
        "R.color.secondary" | false
        "@style/primary\""  | false
    }

}