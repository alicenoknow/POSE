package com.example.pose.presentation.camera

import android.graphics.Bitmap
import kotlin.math.abs

class MotionDetection(var onMotionStopped: () -> Unit,
                      private var diffThreshold: Float = 0.8f,
                      private var stillFramesThreshold: Int = 24) {
    lateinit var prevBitmap: Bitmap
    private var stillFramesCount: Int = 0

    fun detectMotion(bitmap: Bitmap) {
        val scaledBitmap = Bitmap.createScaledBitmap(
            bitmap,
            (bitmap.width * 0.1).toInt(),
            (bitmap.height * 0.1).toInt(),
            false
        )

        if (this::prevBitmap.isInitialized) {
            val difference = getDifferencePercent(scaledBitmap, prevBitmap)
            if (difference > diffThreshold) {
                stillFramesCount = 0
            } else {
                stillFramesCount++
                if (stillFramesCount >= stillFramesThreshold) onMotionStopped()
            }
        }
        prevBitmap = scaledBitmap
    }

    private fun getDifferencePercent(img1: Bitmap, img2: Bitmap): Double {
        if (img1.width != img2.width || img1.height != img2.height) {
            val f = "(%d,%d) vs. (%d,%d)".format(img1.width, img1.height, img2.width, img2.height)
            throw IllegalArgumentException("Images must have the same dimensions: $f")
        }

        var diff = 0L
        for (y in 0 until img1.height) {
            for (x in 0 until img1.width) {
                diff += pixelDiff(img1.getPixel(x, y), img2.getPixel(x, y))
            }
        }
        val maxDiff = 3L * 255 * img1.width * img1.height
        return 100.0 * diff / maxDiff
    }

    private fun pixelDiff(rgb1: Int, rgb2: Int): Int {
        val r1 = (rgb1 shr 16) and 0xff
        val g1 = (rgb1 shr 8) and 0xff
        val b1 = rgb1 and 0xff
        val r2 = (rgb2 shr 16) and 0xff
        val g2 = (rgb2 shr 8) and 0xff
        val b2 = rgb2 and 0xff
        return abs(r1 - r2) + abs(g1 - g2) + abs(b1 - b2)
    }
}