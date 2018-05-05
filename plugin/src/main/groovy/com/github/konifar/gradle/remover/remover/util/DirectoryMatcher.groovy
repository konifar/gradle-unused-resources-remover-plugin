package com.github.konifar.gradle.remover.remover.util

class DirectoryMatcher {

    static boolean match(String dirName, String type) {
        return dirName =~ /\/${type}(-)*/
    }

}