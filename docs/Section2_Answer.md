# Section 2 - Kotlin vs Java exceptions

## What are the key differences between the Kotlin implementation of exceptions and that of Java?

Both languages share almost the same exception hierarchy (`Throwable` -> `Exception` / `Error` -> `RuntimeException` ...), and both use `try`, `catch`, `finally` and `throw`. The main differences are:

1. **No checked exceptions in Kotlin.** In Java, checked exceptions (e.g. `IOException`) are checked at compile time: the method must declare them with `throws` or the caller must catch them. Kotlin treats all exceptions as unchecked, so the compiler never forces the developer to catch or declare anything.
2. **Precondition functions.** Kotlin has `require()` (throws `IllegalArgumentException`, used to validate input), `check()` and `error()` (throw `IllegalStateException`, used to validate state). Java has no equivalent built into the language, so the developer writes `if (...) throw new ...` by hand.
3. **`runCatching`.** Kotlin can wrap code in `runCatching { }`, which returns a `Result` object (`onSuccess` / `onFailure`) instead of using a try/catch block. Java has nothing like it.
4. **`try` and `throw` are expressions.** In Kotlin, a `try` block can return a value (`val x = try { ... } catch (e: Exception) { 0 }`). In Java, `try` is only a statement.
5. **`use` function.** Kotlin's `use { }` closes a `Closeable` resource automatically, similar to Java's try-with-resources.

## How does this affect their usage by the developer?

- Kotlin code is shorter and less cluttered (no `throws` clauses, no empty catch blocks just to satisfy the compiler), and precondition functions make validation a one-liner with a custom message.
- The downside is that the compiler no longer reminds us which exceptions can happen. The developer has to read the documentation and decide by themselves where to catch, otherwise an unhandled exception can crash the app.
- When Kotlin and Java are mixed, Kotlin functions that can throw an exception that Java should catch need the `@Throws` annotation. Without it, Java does not know the exception can be thrown, and Java code that tries to catch a checked exception from that method will not compile.
- `runCatching` lets the developer handle errors in a functional style, but it catches every `Throwable`, so it must be used carefully.

## Sources
- https://kotlinlang.org/docs/exceptions.html
- https://kotlinlang.org/docs/java-interop.html
- https://medium.com/@nehamadiwal/exception-handling-java-vs-kotlin-7dc42340e70b
