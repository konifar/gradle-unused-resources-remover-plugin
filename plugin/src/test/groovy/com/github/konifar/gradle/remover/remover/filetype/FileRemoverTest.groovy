package com.github.konifar.gradle.remover.remover.filetype

import spock.lang.Specification

class FileRemoverTest extends Specification {

    private FileRemover remover = new DrawableFileRemover()

    def "check exclude names"() {
        remover.excludeNames.clear()
        remover.excludeNames.addAll(excludeNames)

        expect:
        remover.isMatchedExcludeNames(targetFilePath) == expected

        where:
        excludeNames                           | targetFilePath                | expected
        ["ic_launcher.xml"]                    | "/res/mipmap/ic_launcher.xml" | true
        ["ic_launcher"]                        | "/res/mipmap/ic_launcher.xml" | true
        ["ic_settings.xml", "ic_launcher.xml"] | "/res/mipmap/ic_launcher.xml" | true
        ["mipmap/ic_launcher"]                 | "/res/mipmap/ic_launcher.xml" | true
        ["ic_launcher.png"]                    | "/res/mipmap/ic_launcher.xml" | false
    }

}