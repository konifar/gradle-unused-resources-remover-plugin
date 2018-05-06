package com.github.konifar.gradle.remover.remover.filetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class MenuFileRemoverTest extends Specification {

    private FileRemover remover = new MenuFileRemover()

    def "type is valid"() {
        expect:
        remover.fileType == "menu"
        remover.resourceName == "menu"
        remover.type == SearchPattern.Type.DEFAULT
    }

    def "pattern matches"() {
        GString pattern = remover.createSearchPattern("menu_main")

        expect:
        FileRemover.isPatternMatched(fileText, pattern) == expected

        where:
        fileText             | expected
        "R.menu.menu_main"   | true
        "@menu/menu_main\""  | true
        "@menu/menu_main<"   | true
        "R.menu.menu_detail" | false
        "@menu/menu_main2\"" | false
        "@layout/menu_main"  | false
    }

}