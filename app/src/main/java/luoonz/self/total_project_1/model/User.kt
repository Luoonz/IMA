package luoonz.self.total_project_1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val uid: Int?,
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "pw") val pw: String,
    @ColumnInfo(name = "names") val names: String
)
