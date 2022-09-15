package pl.krystiankaniowski.composecharts.utils

import kotlin.random.Random

fun List<Float>.randomize(random: Random): List<Float> = FloatArray(this.size) { random.nextFloat() }.toList()
fun generateList(random: Random, size: Int): List<Float> = FloatArray(size) { random.nextFloat() }.toList()