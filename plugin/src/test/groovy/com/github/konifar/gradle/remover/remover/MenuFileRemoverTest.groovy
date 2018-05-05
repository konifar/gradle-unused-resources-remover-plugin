package com.github.konifar.gradle.remover.remover

import spock.lang.Specification

class MenuFileRemoverTest extends Specification {

    def remover = new com.github.konifar.gradle.remover.remover.MenuFileRemover()

    def "type is menu"() {
        expect:
        remover.fileType == "menu"
    }

    def "pattern matches"() {
        def pattern = remover.createSearchPattern("menu_main")
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
    }
}