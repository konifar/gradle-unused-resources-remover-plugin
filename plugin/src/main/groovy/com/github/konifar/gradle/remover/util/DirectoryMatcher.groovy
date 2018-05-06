package com.github.konifar.gradle.remover.util

class DirectoryMatcher {

    static boolean matchLast(String dirName, String type) {
        return dirName =~ /(\/${type}-.*$)|(\/${type}$)/
    }

}