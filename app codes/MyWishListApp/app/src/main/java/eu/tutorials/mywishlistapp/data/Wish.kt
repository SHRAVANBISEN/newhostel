package eu.tutorials.mywishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//REFER THE NOTES TO UNDERSTAND THIS PART
@Entity(tableName="wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name="wish-title")
    val title: String="",
    @ColumnInfo(name="wish-desc")
    val description:String=""
)
//THIS IS THE DUMMY WISH PART WHICH IS OF NO USE AS OF NOW AS WE ARE USING DATA FROM THE DATABASE WE HAVE MADE

object DummyWish{
    val wishList = listOf(
        Wish(title="Google Watch 2",
            description =  "An android Watch"),
        Wish(title = "Oculus Quest 2",
            description = "A VR headset for playing games"),
        Wish(title = "A Sci-fi, Book",
            description= "A science friction book from any best seller"),
        Wish(title = "Bean bag",
            description = "A comfy bean bag to substitute for a chair")
        )
}
