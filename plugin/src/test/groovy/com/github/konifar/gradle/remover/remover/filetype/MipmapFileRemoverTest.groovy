package com.github.konifar.gradle.remover.remover.filetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class MipmapFileRemoverTest extends Specification {

    private FileRemover remover = new MipmapFileRemover()

    def "type is valid"() {
        expect:
        remover.fileType == "mipmap"
        remover.resourceName == "mipmap"
        remover.type == SearchPattern.Type.DRAWABLE
    }

    def "pattern matches"() {
        GString pattern = remover.createSearchPattern("ic_launcher")

        expect:
        FileRemover.isPatternMatched(fileText, pattern) == expected

        where:
        fileText                      | expected
        "R.mipmap.ic_launcher"        | true
        "@mipmap/ic_launcher\""       | true
        "@mipmap/ic_launcher<"        | true
        "R.drawable.ic_launch"        | false
        "@mipmap/ic_launcher_round\"" | false
    }

}