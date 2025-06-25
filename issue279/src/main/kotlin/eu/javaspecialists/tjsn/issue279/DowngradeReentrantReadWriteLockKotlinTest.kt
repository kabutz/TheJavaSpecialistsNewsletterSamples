package eu.javaspecialists.tjsn.issue279

import java.util.*
import java.util.concurrent.locks.*
import kotlin.concurrent.*

fun main() {
    val rwlock = ReentrantReadWriteLock()
    val timer = Timer()
    timer.schedule(object : TimerTask() {
        override fun run() {
            var time = System.nanoTime()
            try {
                rwlock.readLock().lock()
                try {
                    println("Got the read lock")
                } finally {
                    rwlock.readLock().unlock()
                }
            } finally {
                time = System.nanoTime() - time
                System.out.printf("time = %dms%n", time / 1000000)
            }
            timer.cancel()
        }
    }, 100)
    rwlock.write {
        rwlock.read {
            Thread.sleep(500)
        }
    }
}
