package com.github.konifar.gradle.remover.remover.util

import spock.lang.Ignore
import spock.lang.Specification

class DirectoryMatcherTest extends Specification {

    @Ignore
    def "match dir name"() {
        expect:
        DirectoryMatcher.match(dirName, type) == expected

        where:
        type       | dirName                | expected
        "values"   | "/values"              | true
        "values"   | "/values-v21"          | true
        "drawable" | "/drawable"            | true
        "drawable" | "/drawable-hdpi"       | true
        "drawable" | "/drawable-v21"        | true
        "anim"     | "/anim/transition.xml" | true
        "animator" | "/animator"            | true
        "xml"      | "/xml/shortcuts.xml"   | true
        "values"   | "/valuesdir"           | false
        "values"   | "values"               | false
        "anim"     | "/animator"            | false
        "animator" | "/anim"                | false
        "xml"      | "/values/strings.xml"  | false
    }

}