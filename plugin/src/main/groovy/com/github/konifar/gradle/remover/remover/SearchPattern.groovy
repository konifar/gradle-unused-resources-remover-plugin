package com.github.konifar.gradle.remover.remover

import org.gradle.internal.impldep.com.google.common.annotations.VisibleForTesting

class SearchPattern {

    /**
     * @param target is file name or attribute name
     * @return pattern string to grep src
     */
    static GString create(String resourceName, String target, Type type = Type.DEFAULT) {
        switch (type) {
            case Type.STYLE:
                // Considered style override
                def pattern = /(@(${resourceName}|${resourceName}StateList)\/${target}[\s!"#\$%&'()\*\+\-\,\\/:;<=>?@\[\\\]^`{|}~])|(R\.${resourceName}\.${toCamelCaseWithUnderscore(target)})|(${target}\.)|(parent="${target}")/
                return pattern
            case Type.DRAWABLE:
                // Considered 9patch
                target -= ".9"
                def pattern = /(@(${resourceName}|${resourceName}StateList)\/${target}[\s!"#\$%&'()\*\+\-\,\\\/:;<=>?@\[\\\]^`{|}~])|(R\.${resourceName}\.${target})/
                return pattern
            case Type.LAYOUT:
                // Considered data binding
                def pattern = /(@(${resourceName}|${resourceName}StateList)\/${target}[\s!"#\$%&'()\*\+\-\,\\\/:;<=>?@\[\\\]`{|}~])|(R\.${resourceName}\.${target})|(${toCamelCase(target)}Binding)/
                return pattern
            default:
                def pattern = /(@(${resourceName}|${resourceName}StateList)\/${target}[\s!"#\$%&'()\*\+\-\,\\\/:;<=>?@\[\\\]`{|}~])|(R\.${resourceName}\.${target})/
                return pattern
        }
    }

    @VisibleForTesting
    static String toCamelCaseWithUnderscore(String name) {
        return name.replaceAll("(\\.)([A-Za-z0-9])", { Object[] it -> "_${it[2].toUpperCase()}" })
    }

    private static String toCamelCase(String text) {
        return text.replaceAll("(_)([A-Za-z0-9])", { Object[] it -> it[2].toUpperCase() }).capitalize()
    }

    enum Type {
        STYLE,
        DRAWABLE,
        LAYOUT,
        DEFAULT

        static Type from(String type) {
            switch (type) {
                case "style":
                    return STYLE
                case "drawable":
                    return DRAWABLE
                case "layout":
                    return LAYOUT
                default:
                    return DEFAULT
            }
        }
    }

}