package luoonz.self.total_project_1.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import luoonz.self.total_project_1.model.User

// room 에 연결된 Dao 를 위해 @Dao 추가
// Entity 에 조회, 저장, 삭제를 어찌할 것인가를 설정

@Dao
interface UserDao {
    @Query("Select * from user where names like:names")
    fun getUser(names: String):List<User>

    @Insert
    fun insertUser(user: User)

    @Query("Select * from user where id like:id and pw like:pw")
    fun loginUser(id: String, pw: String):List<User>

    @Query("Select * from user")
    fun getAllUser():List<User>

    @Query("Delete from user")
    fun deleteAll()
}