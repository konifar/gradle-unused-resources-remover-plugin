package com.github.konifar.gradle.remover.util

import spock.lang.Specification

class DirectoryMatcherTest extends Specification {

    def "match type with the last part of dir name"() {
        expect:
        DirectoryMatcher.matchLast(dirName, type) == expected

        where:
        type       | dirName               | expected
        "values"   | "/values"             | true
        "values"   | "/values-v21"         | true
        "drawable" | "/drawable"           | true
        "drawable" | "/drawable-hdpi"      | true
        "drawable" | "/drawable-v21"       | true
        "anim"     | "/anim"               | true
        "animator" | "/animator"           | true
        "xml"      | "/xml/shortcuts.xml"  | false
        "values"   | "/valuesdir"          | false
        "values"   | "values"              | false
        "anim"     | "/animator"           | false
        "animator" | "/anim"               | false
        "xml"      | "/values/strings.xml" | false
    }

}