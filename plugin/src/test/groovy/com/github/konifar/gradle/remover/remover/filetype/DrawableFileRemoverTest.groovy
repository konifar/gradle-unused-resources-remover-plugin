package com.github.konifar.gradle.remover.remover.filetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class DrawableFileRemoverTest extends Specification {

    private FileRemover remover = new DrawableFileRemover()

    def "type is valid"() {
        expect:
        remover.fileType == "drawable"
        remover.resourceName == "drawable"
        remover.type == SearchPattern.Type.DRAWABLE
    }

    def "pattern matches"() {
        GString pattern = remover.createSearchPattern(fileName)

        expect:
        FileRemover.isPatternMatched(fileText, pattern) == expected

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

}