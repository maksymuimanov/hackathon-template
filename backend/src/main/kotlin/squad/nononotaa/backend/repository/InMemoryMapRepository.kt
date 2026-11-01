package squad.nononotaa.backend.repository

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.function.Predicate

abstract class InMemoryMapRepository<K : Any, V : Any>(
    protected val storage: ConcurrentMap<K, V> = ConcurrentHashMap(),
) {
    open fun saveAll(values: Iterable<V>): List<V> = values.map { save(it) }

    open fun save(value: V): V {
        val id = value.id()
        return save(id, value)
    }

    open fun save(
        id: K,
        value: V,
    ): V {
        storage[id] = value
        return value
    }

    open fun update(
        id: K,
        updater: (V) -> V,
    ): V? =
        storage.computeIfPresent(id) { _, current ->
            updater(current)
        }

    open fun findById(id: K): V? = storage[id]

    open fun findByKey(id: K): V? = storage[id]

    open fun findAll(): List<V> = storage.values.toList()

    open fun findAllById(ids: Iterable<K>): List<V> = ids.mapNotNull { storage[it] }

    open fun asMap(): Map<K, V> = HashMap(storage)

    open fun existsById(id: K): Boolean = storage.containsKey(id)

    open fun containsKey(id: K): Boolean = storage.containsKey(id)

    open fun count(): Long = storage.size.toLong()

    open fun isEmpty(): Boolean = storage.isEmpty()

    open fun find(predicate: (V) -> Boolean): List<V> = storage.values.filter(predicate)

    open fun find(predicate: Predicate<V>): List<V> = find { predicate.test(it) }

    open fun findAll(predicate: (V) -> Boolean): List<V> = storage.values.filter(predicate)

    open fun findAll(predicate: Predicate<V>): List<V> = findAll { predicate.test(it) }

    open fun findOne(predicate: (V) -> Boolean): V? = storage.values.firstOrNull(predicate)

    open fun findOne(predicate: Predicate<V>): V? = findOne { predicate.test(it) }

    open fun findFirst(predicate: (V) -> Boolean): V? = storage.values.firstOrNull(predicate)

    open fun findFirst(predicate: Predicate<V>): V? = findFirst { predicate.test(it) }

    open fun exists(predicate: (V) -> Boolean): Boolean = storage.values.any(predicate)

    open fun exists(predicate: Predicate<V>): Boolean = exists { predicate.test(it) }

    open fun count(predicate: (V) -> Boolean): Long = storage.values.count(predicate).toLong()

    open fun count(predicate: Predicate<V>): Long = count { predicate.test(it) }

    open fun deleteById(id: K): V? = storage.remove(id)

    open fun deleteByKey(id: K): Boolean = storage.remove(id) != null

    open fun delete(value: V): Boolean {
        val entry = storage.entries.firstOrNull { it.value == value } ?: return false
        return storage.remove(entry.key, entry.value)
    }

    open fun deleteAllById(ids: Iterable<K>) {
        ids.forEach { storage.remove(it) }
    }

    open fun deleteBy(predicate: (V) -> Boolean): List<V> {
        val matchingEntries = storage.entries.filter { predicate(it.value) }
        val removed = mutableListOf<V>()
        for ((id, value) in matchingEntries) {
            if (storage.remove(id, value)) {
                removed.add(value)
            }
        }
        return removed
    }

    open fun deleteBy(predicate: Predicate<V>): List<V> = deleteBy { predicate.test(it) }

    open fun deleteAll() {
        storage.clear()
    }

    open fun clear() {
        storage.clear()
    }

    @Suppress("UNCHECKED_CAST")
    fun V.id(): K =
        this.javaClass.fields
            .find {
                it.isAnnotationPresent(Id::class.java) || it.name == "id"
            }?.get(this) as K
}

@Target(AnnotationTarget.PROPERTY)
annotation class Id
