package eu.javaspecialists.tjsn.issue279

import java.util.concurrent.locks.*
import kotlin.concurrent.*

fun main() {
    val rwlock = ReentrantReadWriteLock()
    println(rwlock) // w=0, r=0
    rwlock.write {
        println(rwlock) // w=1, r=0
        rwlock.read {
            println(rwlock) // w=1, r=1
        }
        println(rwlock) // w=1, r=0
    }
    println(rwlock) // w=0, r=0
}