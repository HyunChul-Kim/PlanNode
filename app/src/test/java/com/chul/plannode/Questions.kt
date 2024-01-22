package com.chul.plannode

object Questions {

    fun mostReceivedGiftQuestion(): Question<Pair<Array<String>, Array<String>>, Int> = Question(
        listOf(
            Case(
                Pair(
                    arrayOf("muzi", "ryan", "frodo", "neo"),
                    arrayOf("muzi frodo", "muzi frodo", "ryan muzi", "ryan muzi", "ryan muzi", "frodo muzi", "frodo ryan", "neo muzi")
                ),
                2
            ),
            Case(
                Pair(
                    arrayOf("joy", "brad", "alessandro", "conan", "david"),
                    arrayOf("alessandro brad", "alessandro joy", "alessandro conan", "david alessandro", "alessandro david")
                ),
                4
            ),
            Case(
                Pair(
                    arrayOf("a", "b", "c"),
                    arrayOf("a b", "b a", "c a", "a c", "a c", "c a")
                ),
                0
            )
        )
    )

    fun donutAndBarGraphQuestion() = Question(
        listOf(
            Case(
                Utils.findBracketGroup("[[2, 3], [4, 3], [1, 1], [2, 1]]", ","),
                intArrayOf(2, 1, 1, 0)
            ),
            Case(
                Utils.findBracketGroup("[[4, 11], [1, 12], [8, 3], [12, 7], [4, 2], [7, 11], [4, 8], [9, 6], [10, 11], [6, 10], [3, 5], [11, 1], [5, 3], [11, 9], [3, 8]]", ","),
                intArrayOf(4, 0, 1, 2)
            )
        )
    )

    fun choiceDice() = Question(
        listOf(
            Case(
                Utils.findBracketGroup("[[1, 2, 3, 4, 5, 6], [3, 3, 3, 3, 4, 4], [1, 3, 3, 4, 4, 4], [1, 1, 4, 4, 5, 5]]", ","),
                intArrayOf(1,4)
            ),
            /*Case(
                Utils.findBracketGroup("[[1, 2, 3, 4, 5, 6], [2, 2, 4, 4, 6, 6]]", ","),
                intArrayOf(2)
            ),
            Case(
                Utils.findBracketGroup("[[40, 41, 42, 43, 44, 45], [43, 43, 42, 42, 41, 41], [1, 1, 80, 80, 80, 80], [70, 70, 1, 1, 70, 70]]", ","),
                intArrayOf(1,3)
            )*/
        )
    )
}