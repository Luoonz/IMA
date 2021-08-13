package luoonz.self.total_project_1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class BMIHistory(
    @PrimaryKey
    val uid: Int?,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "height") val height: Float,
    @ColumnInfo(name = "weight") val weight: Float,
    @ColumnInfo(name = "bmi") val bmi: String
)
