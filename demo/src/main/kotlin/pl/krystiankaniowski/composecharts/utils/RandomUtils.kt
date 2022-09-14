package pl.krystiankaniowski.composecharts

import kotlin.random.Random

fun List<Float>.randomize(random: Random): List<Float> = FloatArray(this.size) { random.nextFloat() }.toList()