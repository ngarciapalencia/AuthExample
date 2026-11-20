package com.example.authexample.exceptions

import android.util.Log

private const val TAG = "EXC" // filter Logcat by this tag

/**
 * Entry point of the exception demos. Everything is printed in Logcat.
 * Comment out any line below to turn a demo off.
 */
fun runExceptionDemos() {
    lightDemo()          // Exceptions 1 - check()
    numberDemo()         // Exceptions 1 - require()
    customExceptionDemo() // Exceptions 2 - custom exception
}

/* ---------------------------------------------------------------
 * EXCEPTIONS 1 - Precondition functions
 * --------------------------------------------------------------- */

class Light(private var on: Boolean) {
    fun isLightOn(): Boolean = on
}

// check() validates the STATE of an object/variable.
// If the condition is false it throws IllegalStateException automatically.
fun useLight(light: Light) {
    check(light.isLightOn()) { "The light is not turned on" }
    Log.d(TAG, "The light is on, we can work")
}

fun lightDemo() {
    val light = Light(on = false) // change to true to see the "no exception" path
    try {
        useLight(light)
    } catch (e: IllegalStateException) {
        // Specific catch block: tells us this exact exception has occurred
        Log.d(TAG, "IllegalStateException caught: ${e.message}")
    }
}

// require() validates a user INPUT (an argument).
// If the condition is false it throws IllegalArgumentException automatically.
fun checkNumber(number: Int) {
    require(number >= 0) { "The number must not be negative, but was $number" }
    Log.d(TAG, "The number $number is valid")
}

fun numberDemo() {
    val userInput = -5 // change to a positive number to see the "no exception" path
    try {
        checkNumber(userInput)
    } catch (e: IllegalArgumentException) {
        Log.d(TAG, "IllegalArgumentException caught: ${e.message}")
    }
}

/* ---------------------------------------------------------------
 * EXCEPTIONS 2 - Custom exception
 * --------------------------------------------------------------- */

// "open" -> the exception itself can be extended by other classes.
// We extend Exception and override toString() to make it obvious that it is ours.
open class WordTooLongException(message: String) : Exception(message) {
    override fun toString(): String = "WordTooLongException -> $message"
}

// Example of extending our own exception (proves the class is open)
class WordWayTooLongException(message: String) : WordTooLongException(message)

// The code does not know by itself when this exception happens, so we provoke it with "throw"
fun checkWord(word: String) {
    if (word.length > 10) {
        throw WordTooLongException("The word '$word' has ${word.length} characters (max is 10)")
    }
    Log.d(TAG, "The word '$word' is OK")
}

fun customExceptionDemo() {
    try {
        checkWord("hello")                 // OK
        checkWord("extraordinarily")       // provokes the exception
    } catch (e: WordTooLongException) {
        Log.d(TAG, "Custom exception caught: $e")
    }

    // Uncomment to see the extended exception being thrown and caught by the parent type
    /*
    try {
        throw WordWayTooLongException("This one extends WordTooLongException")
    } catch (e: WordTooLongException) {
        Log.d(TAG, "Caught the child through the parent: $e")
    }
    */
}
