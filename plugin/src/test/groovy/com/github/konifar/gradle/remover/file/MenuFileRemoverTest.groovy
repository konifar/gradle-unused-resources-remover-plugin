package com.github.konifar.gradle.remover.file

import spock.lang.Specification

class MenuFileRemoverTest extends Specification {

    def remover = new MenuFileRemover()

    def "type is menu"() {
        expect:
        remover.type == "menu"
    }

    def "pattern matches"() {
        def pattern = remover.createPattern("menu_main")
        def isMatched = false
        if (fileText =~ pattern) {
            isMatched = true
        }

        expect:
        isMatched == expected

        where:
        fileText             | expected
        "R.menu.menu_main"   | true
        "@menu/menu_main\""  | true
        "R.menu.menu_detail" | false
        "@menu/menu_main2\"" | false
        "@layout/menu_main"  | false
        // "R.menu.menu_main2"   | false
    }
}