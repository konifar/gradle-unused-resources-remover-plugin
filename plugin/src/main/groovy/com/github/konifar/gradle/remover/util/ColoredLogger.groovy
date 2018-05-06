package com.github.konifar.gradle.remover.util

class ColoredLogger {

    private static final String ANSI_RESET = "\u001B[0m"
    private static final String ANSI_GREEN = "\u001B[32m"
    private static final String ANSI_YELLOW = "\u001B[33m"

    static def logGreen(String text) {
        println "${ANSI_GREEN}${text}${ANSI_RESET}"
    }

    static def logYellow(String text) {
        println "${ANSI_YELLOW}${text}${ANSI_RESET}"
    }

    static def log(String text) {
        println text
    }

}