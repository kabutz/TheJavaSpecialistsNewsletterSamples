package eu.javaspecialists.tjsn.issue279

import java.util.*
import java.util.concurrent.locks.*
import kotlin.concurrent.*

fun main() {
    val rwlock = ReentrantReadWriteLock()
    val threadNames = ArrayList<String>()
    for (i in 1..8) {
        thread(start = true) {
            val name = Thread.currentThread().name
            for (j in 0..999999) {
                rwlock.write {
                    threadNames.add(name)
                }
                rwlock.write {
                    threadNames.add(name)
                }
                rwlock.read {
                    val it = threadNames.iterator()
                    while (it.hasNext()) {
                        val next = it.next()
                        if (next == name) {
                            rwlock.write {
                                it.remove()
                            }
                        }
                    }
                }
            }
        }
    }
}
