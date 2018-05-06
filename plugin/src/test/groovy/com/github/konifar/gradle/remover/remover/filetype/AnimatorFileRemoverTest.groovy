package com.github.konifar.gradle.remover.remover.filetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class AnimatorFileRemoverTest extends Specification {

    FileRemover remover = new AnimatorFileRemover()

    def "type is valid"() {
        expect:
        remover.fileType == "animator"
        remover.resourceName == "animator"
        remover.type == SearchPattern.Type.DEFAULT
    }

    def "matches pattern"() {
        GString pattern = remover.createSearchPattern("fade_animation")

        expect:
        FileRemover.isPatternMatched(fileText, pattern) == expected

        where:
        fileText                      | expected
        "R.animator.fade_animation"   | true
        "R.animator.fade_animation)"  | true
        "R.animator.fade_animation\"" | true
        "@animator/fade_animation\""  | true
        "@animator/fade_animation<"   | true
        "@animator/fade_animation2"   | false
        "R.animator.scale_animation"  | false
        "@anim/fade_animation"        | false
    }

}