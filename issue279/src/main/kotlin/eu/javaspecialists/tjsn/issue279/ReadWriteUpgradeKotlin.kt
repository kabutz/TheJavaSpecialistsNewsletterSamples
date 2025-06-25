package eu.javaspecialists.tjsn.issue279

import java.util.concurrent.locks.*
import kotlin.concurrent.*

fun main() {
    val rwlock = ReentrantReadWriteLock()
    rwlock.read {
        rwlock.write {
            println("We will see this :-)")
        }
    }
}
