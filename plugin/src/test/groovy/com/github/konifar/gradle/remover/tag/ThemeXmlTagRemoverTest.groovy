package com.github.konifar.gradle.remover.tag;

import spock.lang.Specification;

class ThemeXmlTagRemoverTest extends Specification {

    def remover = new ThemeXmlTagRemover()

    def "type is theme"() {
        expect:
        remover.fileType == "theme"
    }

    def "pattern matches"() {
        def pattern = remover.createPattern("AppTheme.Translucent")
        def isMatched = false
        if (fileText =~ pattern) {
            isMatched = true
        }

        expect:
        isMatched == expected

        where:
        fileText                          | expected
        "R.style.AppTheme_Translucent"    | true
        "@style/AppTheme.Translucent\""   | true
        "parent=\"AppTheme.Translucent\"" | true
        "@style/AppTheme.Translucent."    | true
        "R.style.AppTheme.Trans"          | false
        "@style/AppTheme.Translucent2\""  | false
        // "R.style.AppTheme.Translucent2"   | false
    }

}