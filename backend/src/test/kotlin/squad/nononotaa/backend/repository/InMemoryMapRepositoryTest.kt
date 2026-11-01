package squad.nononotaa.backend.repository

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InMemoryMapRepositoryTest {
    data class Item(
        @JvmField
        @Id
        val id: String,
        val name: String,
        val value: Int,
    )

    private lateinit var repository: InMemoryMapRepository<String, Item>

    @BeforeEach
    fun setUp() {
        repository = object : InMemoryMapRepository<String, Item>() {}
    }

    @Test
    fun `test save and findById`() {
        val item = Item("1", "First", 100)
        val saved = repository.save(item)
        assertEquals(item, saved)

        val found = repository.findById("1")
        assertEquals(item, found)

        val notFound = repository.findById("999")
        assertNull(notFound)
    }

    @Test
    fun `test save with explicit id and findByKey`() {
        val item = Item("1", "First", 100)
        repository.save("1", item)

        val found = repository.findByKey("1")
        assertEquals(item, found)

        val notFound = repository.findByKey("999")
        assertNull(notFound)
    }

    @Test
    fun `test saveAll and findAll`() {
        val items =
            listOf(
                Item("1", "First", 100),
                Item("2", "Second", 200),
                Item("3", "Third", 300),
            )
        val saved = repository.saveAll(items)
        assertEquals(3, saved.size)

        val all = repository.findAll()
        assertEquals(3, all.size)

        val byIds = repository.findAllById(listOf("1", "3", "unknown"))
        assertEquals(2, byIds.size)
    }

    @Test
    fun `test update`() {
        val item = Item("1", "Original", 100)
        repository.save(item)

        val updated = repository.update("1") { it.copy(name = "Updated", value = 150) }
        assertNotNull(updated)
        assertEquals("Updated", updated?.name)
        assertEquals(150, updated?.value)

        val notFoundUpdate = repository.update("999") { it.copy(name = "None") }
        assertNull(notFoundUpdate)
    }

    @Test
    fun `test existsById and containsKey and count and isEmpty`() {
        assertTrue(repository.isEmpty())
        assertEquals(0, repository.count())
        assertFalse(repository.existsById("1"))
        assertFalse(repository.containsKey("1"))

        repository.save(Item("1", "First", 100))

        assertFalse(repository.isEmpty())
        assertEquals(1, repository.count())
        assertTrue(repository.existsById("1"))
        assertTrue(repository.containsKey("1"))
    }

    @Test
    fun `test predicate operations`() {
        val items =
            listOf(
                Item("1", "Alpha", 10),
                Item("2", "Beta", 20),
                Item("3", "Gamma", 30),
            )
        repository.saveAll(items)

        val matched = repository.find { it.value > 15 }
        assertEquals(2, matched.size)

        val allMatched = repository.findAll { it.value > 15 }
        assertEquals(2, allMatched.size)

        val one = repository.findOne { it.name == "Beta" }
        assertEquals("2", one?.id)

        val first = repository.findFirst { it.value >= 20 }
        assertNotNull(first)

        assertTrue(repository.exists { it.name == "Alpha" })
        assertFalse(repository.exists { it.name == "Delta" })

        assertEquals(2, repository.count { it.value >= 20 })
    }

    @Test
    fun `test deleteById and deleteByKey and delete`() {
        val item1 = Item("1", "First", 100)
        val item2 = Item("2", "Second", 200)
        val item3 = Item("3", "Third", 300)
        repository.saveAll(listOf(item1, item2, item3))

        val deleted1 = repository.deleteById("1")
        assertEquals(item1, deleted1)
        assertNull(repository.deleteById("1"))

        assertTrue(repository.deleteByKey("2"))
        assertFalse(repository.deleteByKey("2"))

        assertTrue(repository.delete(item3))
        assertFalse(repository.delete(item3))
    }

    @Test
    fun `test deleteAllById and deleteBy and deleteAll and clear`() {
        val items = (1..5).map { Item(it.toString(), "Item $it", it * 10) }
        repository.saveAll(items)

        repository.deleteAllById(listOf("1", "2"))
        assertEquals(3, repository.count())

        val removed = repository.deleteBy { it.value >= 40 }
        assertEquals(2, removed.size)
        assertEquals(1, repository.count())

        repository.deleteAll()
        assertTrue(repository.isEmpty())

        repository.save(Item("1", "First", 100))
        assertEquals(1, repository.count())
        repository.clear()
        assertTrue(repository.isEmpty())
    }

    @Test
    fun `test asMap`() {
        val item = Item("1", "First", 100)
        repository.save(item)
        val map = repository.asMap()
        assertEquals(1, map.size)
        assertEquals(item, map["1"])
    }
}
