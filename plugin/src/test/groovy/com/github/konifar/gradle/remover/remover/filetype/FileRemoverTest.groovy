package com.github.konifar.gradle.remover.remover.filetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class FileRemoverTest extends Specification {

    def "type is animator"() {
        FileRemover remover = new FileRemover("animator", "animator")
        GString pattern = remover.createSearchPattern("fade_animation")
        boolean isMatched = FileRemover.isPatternMatched(fileText, pattern)

        expect:
        isMatched == expected

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

    def "type is anim"() {
        FileRemover remover = new FileRemover("anim", "anim")
        GString pattern = remover.createSearchPattern("fade_transition")
        boolean isMatched = FileRemover.isPatternMatched(fileText, pattern)

        expect:
        isMatched == expected

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

    def "type is color"() {
        FileRemover remover = new FileRemover("color", "color")
        GString pattern = remover.createSearchPattern("primary")
        boolean isMatched = FileRemover.isPatternMatched(fileText, pattern)

        expect:
        isMatched == expected

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

    def "type is drawable"() {
        FileRemover remover = new FileRemover("drawable", "drawable", SearchPattern.Type.DRAWABLE)
        GString pattern = remover.createSearchPattern(fileName)
        boolean isMatched = FileRemover.isPatternMatched(fileText, pattern)

        expect:
        isMatched == expected

        where:
        fileName        | fileText                   | expected
        "ic_settings"   | "R.drawable.ic_settings"   | true
        "ic_settings"   | "@drawable/ic_settings\""  | true
        "ic_settings"   | "@drawable/ic_settings<"   | true
        "img_balloon.9" | "@drawable/img_balloon\""  | true
        "img_balloon.9" | "@drawable/img_balloon2\"" | false
        "ic_settings"   | "R.drawable.ic_setting"    | false
        "ic_settings"   | "@mipmap/ic_settings"      | false
    }

    def "type is layout"() {
        FileRemover remover = new FileRemover("layout", "layout", SearchPattern.Type.LAYOUT)
        GString pattern = remover.createSearchPattern("activity_main")
        boolean isMatched = FileRemover.isPatternMatched(fileText, pattern)

        expect:
        isMatched == expected

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

    def "type is menu"() {
        FileRemover remover = new FileRemover("menu", "menu")
        GString pattern = remover.createSearchPattern("menu_main")
        boolean isMatched = FileRemover.isPatternMatched(fileText, pattern)

        expect:
        isMatched == expected

        where:
        fileText             | expected
        "R.menu.menu_main"   | true
        "@menu/menu_main\""  | true
        "@menu/menu_main<"   | true
        "R.menu.menu_detail" | false
        "@menu/menu_main2\"" | false
        "@layout/menu_main"  | false
    }

    def "type is mipmap"() {
        FileRemover remover = new FileRemover("mipmap", "mipmap")
        GString pattern = remover.createSearchPattern("ic_launcher")
        boolean isMatched = FileRemover.isPatternMatched(fileText, pattern)

        expect:
        isMatched == expected

        where:
        fileText                      | expected
        "R.mipmap.ic_launcher"        | true
        "@mipmap/ic_launcher\""       | true
        "@mipmap/ic_launcher<"        | true
        "R.drawable.ic_launch"        | false
        "@mipmap/ic_launcher_round\"" | false
    }

}