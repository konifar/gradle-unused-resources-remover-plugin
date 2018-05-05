package com.github.konifar.gradle.remover.remover.util

class ColoredLogger {

    private static final String ANSI_RESET = "\u001B[0m"
    private static final String ANSI_GREEN = "\u001B[32m"

    static def printlnGreen(String text) {
        println("${ANSI_GREEN}${text}${ANSI_RESET}")
    }

}