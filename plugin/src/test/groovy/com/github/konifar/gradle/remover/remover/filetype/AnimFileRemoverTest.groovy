package com.github.konifar.gradle.remover.remover.filetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class AnimFileRemoverTest extends Specification {

    FileRemover remover = new AnimFileRemover()

    def "type is valid"() {
        expect:
        remover.fileType == "anim"
        remover.resourceName == "anim"
        remover.type == SearchPattern.Type.DEFAULT
    }

    def "matches pattern"() {
        GString pattern = remover.createSearchPattern("fade_transition")

        expect:
        FileRemover.isPatternMatched(fileText, pattern) == expected

        where:
        fileText                       | expected
        "R.anim.fade_transition"       | true
        "R.anim.fade_transition)"      | true
        "R.anim.fade_transition\""     | true
        "@anim/fade_transition\""      | true
        "@anim/fade_transition<"       | true
        "R.animator.fade_transition\"" | false
        "@anim/fade_transition2"       | false
        "R.anim.scale_transition"      | false
        "@animator/fade_transition"    | false
    }

}