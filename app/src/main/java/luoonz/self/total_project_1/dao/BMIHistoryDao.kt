package luoonz.self.total_project_1.dao

import androidx.room.Dao
import androidx.room.Insert
import luoonz.self.total_project_1.model.BMIHistory

@Dao
interface BMIHistoryDao {

    @Insert
    fun insertBmiHistory(bmihistory: BMIHistory)
}