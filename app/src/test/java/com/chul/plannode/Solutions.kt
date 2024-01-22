package com.chul.plannode

object Solutions {

    fun mostReceivedGift(friends: Array<String>, gifts: Array<String>): Int {
        val indexMap = hashMapOf<String, Int>()
        val histories = Array(friends.size) { IntArray(friends.size) { 0 } }
        val factors = IntArray(friends.size) { 0 }
        var answer = 0
        friends.forEachIndexed { index, friend ->
            indexMap[friend] = index
        }
        gifts.forEach { gift ->
            val info = gift.split(" ")
            val senderIndex = indexMap[info.first()] ?: return@forEach
            val receiverIndex = indexMap[info.last()] ?: return@forEach
            histories[senderIndex][receiverIndex]++
            factors[senderIndex]++
            factors[receiverIndex]--
        }
        friends.forEachIndexed { senderIndex, _ ->
            var count = 0
            histories[senderIndex].forEachIndexed receiver@{ receiverIndex, sendCount ->
                if(senderIndex == receiverIndex) return@receiver
                if(sendCount > histories[receiverIndex][senderIndex]) {
                    count++
                } else if(sendCount == histories[receiverIndex][senderIndex]
                    && factors[senderIndex] > factors[receiverIndex]) {
                    count++
                }
            }
            if(count > answer) {
                answer = count
            }
        }
        return answer
    }

    fun donutAndBarGraph(edges: Array<IntArray>): IntArray {
        val answer = IntArray(4) { 0 }
        val inAndOutCount = hashMapOf<Int, IntArray>()
        edges.forEach { edge ->
            inAndOutCount.getOrPut(edge[0]) { IntArray(2) { 0 } }[0]++
            inAndOutCount.getOrPut(edge[1]) { IntArray(2) { 0 } }[1]++
        }
        inAndOutCount.entries.forEach { entry ->
            val counts = entry.value
            when {
                counts[0] >= 2 && counts[1] == 0 -> {
                    answer[0] = entry.key
                }
                counts[0] == 0 && counts[1] > 0 -> {
                    answer[2]++
                }
                counts[0] >= 2 && counts[1] >= 2 -> {
                    answer[3]++
                }
            }
        }
        answer[1] = inAndOutCount.getOrPut(answer[0]) { IntArray(2) { 0 } }[0] - answer[2] - answer[3]
        return answer
    }

    fun pickDice(dice: Array<IntArray>): IntArray {
        var answer: IntArray = intArrayOf()
        pick(0, arrayListOf(), dice)
        return answer
    }

    fun pick(pickIndex: Int, selectedDice: ArrayList<Int>, dice: Array<IntArray>): Int {
        if(selectedDice.size == dice.size / 2) {
            sum()
            println("[${selectedDice[0]}, ${selectedDice[1]}]")
            return 0
        }
        for(i in pickIndex until dice.size) {
            selectedDice.add(i)
            pick(i + 1, selectedDice, dice)
        }
        return 0
    }

    fun sum() {

    }

    fun calculateWinCount(dice1: IntArray, dice2: IntArray): Int {
        val min = dice2.min()
        return dice1.count { it > min }
    }
}