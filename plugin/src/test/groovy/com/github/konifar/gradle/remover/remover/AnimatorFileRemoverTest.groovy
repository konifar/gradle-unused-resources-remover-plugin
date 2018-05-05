package com.github.konifar.gradle.remover.remover

import spock.lang.Specification

class AnimatorFileRemoverTest extends Specification {

    def remover = new com.github.konifar.gradle.remover.remover.AnimatorFileRemover()

    def "type is animator"() {
        expect:
        remover.fileType == "animator"
    }

    def "pattern matches"() {
        def pattern = remover.createSearchPattern("fade_animation")
        def isMatched = false
        if (fileText =~ pattern) {
            isMatched = true
        }

        expect:
        isMatched == expected

        where:
        fileText                      | expected
        "R.animator.fade_animation"   | true
        "R.animator.fade_animation)"  | true
        "R.animator.fade_animation\"" | true
        "@animator/fade_animation\""  | true
        "@animator/fade_animation2"   | false
        "R.animator.scale_animation"  | false
        "@anim/fade_animation"        | false
        // "R.anim.test2"   | false
        // "@anim/test2"    | false
    }
}