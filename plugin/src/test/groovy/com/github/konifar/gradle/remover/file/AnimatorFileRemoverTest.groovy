package com.github.konifar.gradle.remover.file

import spock.lang.Specification

class AnimatorFileRemoverTest extends Specification {

    def remover = new AnimatorFileRemover()

    def "type is animator"() {
        expect:
        remover.type == "animator"
    }

    def "pattern matches"() {
        def pattern = remover.createPattern("fade_animation")
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