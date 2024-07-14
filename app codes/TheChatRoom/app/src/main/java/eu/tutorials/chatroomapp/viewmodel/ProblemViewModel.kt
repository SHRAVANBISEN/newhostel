import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import eu.tutorials.chatroomapp.data.Wish
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WishViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _wishes = MutableStateFlow<List<Wish>>(emptyList())
    val wishes: StateFlow<List<Wish>> get() = _wishes

    private val _wishTitleState = MutableStateFlow("")
    val wishTitleState: StateFlow<String> get() = _wishTitleState

    private val _wishDescriptionState = MutableStateFlow("")
    val wishDescriptionState: StateFlow<String> get() = _wishDescriptionState

    fun loadWishes() {
        val userId = auth.currentUser?.uid ?: return
        db.collection("wishes")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                val wishList = documents.map { doc -> doc.toObject(Wish::class.java) }
                _wishes.value = wishList
            }
    }

    fun addWish(userId: String) {
        val wish = Wish(
            title = _wishTitleState.value,
            description = _wishDescriptionState.value,
            userId = userId
        )
        db.collection("wishes")
            .add(wish)
            .addOnSuccessListener {
                // Clear the state variables after successful addition
                _wishTitleState.value = ""
                _wishDescriptionState.value = ""
            }
            .addOnFailureListener {
                // Handle failure
            }
    }

    fun deleteWish(wish: Wish) {
        db.collection("wishes").document(wish.id)
            .delete()
            .addOnSuccessListener {
                loadWishes()
            }
    }

    fun onWishTitleChanged(newTitle: String) {
        _wishTitleState.value = newTitle
    }

    fun onWishDescriptionChanged(newDescription: String) {
        _wishDescriptionState.value = newDescription
    }
}
