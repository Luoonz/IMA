package luoonz.self.total_project_1

import androidx.room.Database
import androidx.room.RoomDatabase
import luoonz.self.total_project_1.dao.BMIHistoryDao
import luoonz.self.total_project_1.dao.UserDao
import luoonz.self.total_project_1.model.BMIHistory
import luoonz.self.total_project_1.model.User

@Database(entities = [User::class, BMIHistory::class], version = 2)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao
    abstract fun bmiDao() : BMIHistoryDao
}