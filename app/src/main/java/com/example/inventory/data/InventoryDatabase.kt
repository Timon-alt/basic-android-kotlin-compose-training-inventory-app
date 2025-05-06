package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Класс базы данных с паттерном 'Одиночка'
 */
@Database(
    entities = [Item::class], // список сущностей
    version = 1,              // версия базы данных
    exportSchema = false      // не сохранять историю схемы
)
abstract class InventoryDatabase : RoomDatabase() {
    /**
     * Метод получения ItemDao
     */
    abstract fun itemDao(): ItemDao

    companion object {
        /**
         * Переменная [Instance] хранит ссылку на базу данных после её создания. Это помогает
         * поддерживать единственный экземпляр открытой базы данных в определённый момент времени,
         * так как её создание и поддержка — ресурсоёмкие операции.
         *
         * Значение Volatile-переменной никогда не кэшируется, и все операции чтения и записи
         * происходят напрямую в основной памяти. Это гарантирует, что значение [Instance] всегда
         * актуально и одинаково для всех потоков выполнения. То есть изменения, внесённые одним
         * потоком в Instance, сразу становятся видны всем остальным потокам.
         */
        @Volatile
        private var Instance: InventoryDatabase? = null

        /**
         * Если [Instance] != null, вернуть значение, иначе создать новый экземпляр базы данных
         */
        fun getDatabase(context: Context): InventoryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}