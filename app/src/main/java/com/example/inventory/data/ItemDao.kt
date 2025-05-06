package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Item DAO(Data Access Object) provides convenience methods for
 * querying/retrieving, inserting, deleting, and updating the database.
 */
@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    /**
     * С возвращаемым типом Flow мы получаем уведомления при каждом изменении данных в базе данных.
     * Room автоматически обновляет этот Flow, что означает, что нам нужно явно получать данные
     * только один раз. Благодаря возвращаемому типу Flow, Room
     * также выполняет запрос в фоновом потоке. Нам не нужно явно делать функцию suspend и вызывать
     * её внутри корутины.
     */
    @Query("SELECT * FROM items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Query("SELECT * FROM items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>
}