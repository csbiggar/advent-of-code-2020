package day7

import helpers.FileReader

private val input = FileReader("/day7/input.txt").readLines()

/*
  D7#1 - 296 bags could eventually contain my bag
  D7#2 - my bag contains 9339 others

 */

fun main() {
    val myBag = Bag("shiny gold")
    val rules = input.map {
        BagRule.fromText(it)
    }
    println("All rules mapped! Now to find my bag ...")

    val bagsContainingMine = FindMyBag(myBag, rules).insideWhichOthers()
    println("${bagsContainingMine.size} bags could eventually contain my $myBag ")

    val myBagContains = FindMyBag(myBag, rules).containsHowManyOthers()
    println("My bag contains $myBagContains others")
}


data class Bag(val id: String)
data class Quantity(val value: Int) {
    operator fun plus(another: Quantity) = Quantity(this.value + another.value)
    operator fun times(another: Quantity) = Quantity(this.value * another.value)
}

data class BagRule(
    val parent: Bag,
    val children: Map<Bag, Quantity>
) {
    companion object {
        fun fromText(text: String): BagRule {
            val parent = Bag(text.substringBefore("bag").trim())

            val children = text
                .substringAfter("contain")
                .split(",")
                .mapNotNull {
                    val parts = it.split(" ")
                    parts[1].toIntOrNull()
                        ?.let { quantity -> Bag("${parts[2]} ${parts[3]}") to Quantity(quantity) }

                }
                .toMap()

            return BagRule(parent, children)
        }
    }
}

class FindMyBag(private val myBag: Bag, private val rules: List<BagRule>) {

    tailrec fun insideWhichOthers(
        bagRules: List<BagRule> = rules,
        bagsContainingMine: List<Bag> = emptyList()
    ): List<Bag> {

        val (containsMine, doesNotContainMine) = bagRules
            .filterNot { it.parent == myBag }
            .filterNot { it.children.isEmpty() }
            .partition { (listOf(myBag) + bagsContainingMine).intersect(it.children.keys).isNotEmpty() }

        val runningTotal = containsMine.map { it.parent } + bagsContainingMine

        if (
            doesNotContainMine.isEmpty()    // Nothing more to search
            || containsMine.isEmpty() // No more found containing mine, stop searching
        ) {
            return runningTotal
        }
        return insideWhichOthers(doesNotContainMine, runningTotal)
    }

    fun containsHowManyOthers(bag: Bag = myBag): Int {
        return rules
            .firstOrNull { it.parent == bag }
            ?.children
            ?.map { (childBag, quantity) ->
                quantity.value + quantity.value * containsHowManyOthers(childBag)
            }
            ?.sum()
            ?: 0
    }
}

/*

gold  -> 1 x olive, 2 x plum
olive -> 3 x blue, 4 x black
plum  -> 5 x blue, 6 x black

 */

