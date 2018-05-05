package com.github.konifar.gradle.remover.tag

class ThemeXmlTagRemover extends StyleXmlTagRemover {

    @Override
    String getType() {
        return "theme"
    }

    @Override
    String getTagName() {
        return "style"
    }

}