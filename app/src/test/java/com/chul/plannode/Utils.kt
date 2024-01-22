package com.chul.plannode

import java.util.regex.Pattern

object Utils {
    fun findBracketGroup(input: String, delimiters: String): Array<IntArray> {
        val result = mutableListOf<IntArray>()
        val rex = "\\[([^\\]\\[\\r\\n]*)\\]"
        val pattern = Pattern.compile(rex)
        val matcher = pattern.matcher(input)
        while(matcher.find()) {
            matcher.group(1)?.let { group ->
                result.add(group.split(delimiters).map { it.trim().toInt() }.toIntArray())
            }
        }
        return result.toTypedArray()
    }

    fun binarySearch(list: IntArray, start: Int, end: Int, target: Int, default: Int = -1): Int {
        if(start > end) return default
        val mid = (start + end) / 2
        val value = list[mid]
        return if(value == target) {
            mid
        } else if(value < target) {
            binarySearch(list, target, start, mid - 1, default)
        } else {
            binarySearch(list, target, mid + 1, end, default)
        }
    }
}