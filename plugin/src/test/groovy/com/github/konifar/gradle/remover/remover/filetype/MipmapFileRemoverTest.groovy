package com.github.konifar.gradle.remover.remover.filetype

import spock.lang.Specification

class MipmapFileRemoverTest extends Specification {

    def remover = new com.github.konifar.gradle.remover.remover.filetype.MipmapFileRemover()

    def "type is mipmap"() {
        expect:
        remover.fileType == "mipmap"
    }

    def "pattern matches"() {
        def pattern = remover.createSearchPattern("ic_launcher")
        def isMatched = false
        if (fileText =~ pattern) {
            isMatched = true
        }

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