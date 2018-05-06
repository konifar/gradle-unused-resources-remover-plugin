package com.github.konifar.gradle.remover.remover.valuetype

import com.github.konifar.gradle.remover.remover.SearchPattern
import spock.lang.Specification

class ThemeXmlValueRemoverTest extends Specification {

    private XmlValueRemover remover = new ThemeXmlValueRemover()

    def "type is valid"() {
        expect:
        remover.fileType == "theme"
        remover.resourceName == "style"
        remover.tagName == "style"
        remover.type == SearchPattern.Type.STYLE
    }

    def "pattern matches"() {
        GString pattern = remover.createSearchPattern("AppTheme.Translucent")

        expect:
        XmlValueRemover.isPatternMatched(fileText, pattern) == expected

        where:
        fileText                          | expected
        "R.style.AppTheme_Translucent"    | true
        "@style/AppTheme.Translucent\""   | true
        "@style/AppTheme.Translucent<"    | true
        "parent=\"AppTheme.Translucent\"" | true
        "@style/AppTheme.Translucent."    | true
        "R.style.AppTheme.Trans"          | false
        "@style/AppTheme.Translucent2\""  | false
    }

}