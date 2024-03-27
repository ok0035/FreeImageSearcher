package com.zerosword.data.response


import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.zerosword.domain.model.PhotoModel

class GetPhotosRes : ArrayList<GetPhotosRes.GetPhotosResItem>(){
    @Keep
    @Parcelize
    data class GetPhotosResItem(
        @SerializedName("blur_hash")
        val blurHash: String? = "", // LoC%a7IoIVxZ_NM|M{s:%hRjWAo0
        @SerializedName("color")
        val color: String? = "", // #60544D
        @SerializedName("created_at")
        val createdAt: String? = "", // 2016-05-03T11:00:28-04:00
        @SerializedName("current_user_collections")
        val currentUserCollections: List<CurrentUserCollection?>? = listOf(),
        @SerializedName("description")
        val description: String? = "", // A man drinking a coffee.
        @SerializedName("height")
        val height: Int? = 0, // 3497
        @SerializedName("id")
        val id: String? = "", // LBI7cgq3pbM
        @SerializedName("liked_by_user")
        val likedByUser: Boolean? = false, // false
        @SerializedName("likes")
        val likes: Int? = 0, // 12
        @SerializedName("links")
        val links: Links? = Links(),
        @SerializedName("updated_at")
        val updatedAt: String? = "", // 2016-07-10T11:00:01-05:00
        @SerializedName("urls")
        val urls: Urls? = Urls(),
        @SerializedName("user")
        val user: User? = User(),
        @SerializedName("width")
        val width: Int? = 0 // 5245
    ) : Parcelable {
        @Keep
        @Parcelize
        data class CurrentUserCollection(
//            @SerializedName("cover_photo")
//            val coverPhoto: Any? = Any(), // null
            @SerializedName("id")
            val id: Int? = 0, // 206
            @SerializedName("last_collected_at")
            val lastCollectedAt: String? = "", // 2016-06-02T13:10:03-04:00
            @SerializedName("published_at")
            val publishedAt: String? = "", // 2016-01-12T18:16:09-05:00
            @SerializedName("title")
            val title: String? = "", // Makers: Cat and Ben
            @SerializedName("updated_at")
            val updatedAt: String? = "", // 2016-07-10T11:00:01-05:00
//            @SerializedName("user")
//            val user: Any? = Any() // null
        ) : Parcelable
    
        @Keep
        @Parcelize
        data class Links(
            @SerializedName("download")
            val download: String? = "", // https://unsplash.com/photos/LBI7cgq3pbM/download
            @SerializedName("download_location")
            val downloadLocation: String? = "", // https://api.unsplash.com/photos/LBI7cgq3pbM/download
            @SerializedName("html")
            val html: String? = "", // https://unsplash.com/photos/LBI7cgq3pbM
            @SerializedName("self")
            val self: String? = "" // https://api.unsplash.com/photos/LBI7cgq3pbM
        ) : Parcelable
    
        @Keep
        @Parcelize
        data class Urls(
            @SerializedName("full")
            val full: String? = "", // https://images.unsplash.com/face-springmorning.jpg?q=75&fm=jpg
            @SerializedName("raw")
            val raw: String? = "", // https://images.unsplash.com/face-springmorning.jpg
            @SerializedName("regular")
            val regular: String? = "", // https://images.unsplash.com/face-springmorning.jpg?q=75&fm=jpg&w=1080&fit=max
            @SerializedName("small")
            val small: String? = "", // https://images.unsplash.com/face-springmorning.jpg?q=75&fm=jpg&w=400&fit=max
            @SerializedName("thumb")
            val thumb: String? = "" // https://images.unsplash.com/face-springmorning.jpg?q=75&fm=jpg&w=200&fit=max
        ) : Parcelable
    
        @Keep
        @Parcelize
        data class User(
            @SerializedName("bio")
            val bio: String? = "", // XO
            @SerializedName("id")
            val id: String? = "", // pXhwzz1JtQU
            @SerializedName("instagram_username")
            val instagramUsername: String? = "", // instantgrammer
            @SerializedName("links")
            val links: Links? = Links(),
            @SerializedName("location")
            val location: String? = "", // Way out there
            @SerializedName("name")
            val name: String? = "", // Gilbert Kane
            @SerializedName("portfolio_url")
            val portfolioUrl: String? = "", // https://theylooklikeeggsorsomething.com/
            @SerializedName("profile_image")
            val profileImage: ProfileImage? = ProfileImage(),
            @SerializedName("total_collections")
            val totalCollections: Int? = 0, // 52
            @SerializedName("total_likes")
            val totalLikes: Int? = 0, // 5
            @SerializedName("total_photos")
            val totalPhotos: Int? = 0, // 74
            @SerializedName("twitter_username")
            val twitterUsername: String? = "", // crew
            @SerializedName("username")
            val username: String? = "" // poorkane
        ) : Parcelable {
            @Keep
            @Parcelize
            data class Links(
                @SerializedName("html")
                val html: String? = "", // https://unsplash.com/poorkane
                @SerializedName("likes")
                val likes: String? = "", // https://api.unsplash.com/users/poorkane/likes
                @SerializedName("photos")
                val photos: String? = "", // https://api.unsplash.com/users/poorkane/photos
                @SerializedName("portfolio")
                val portfolio: String? = "", // https://api.unsplash.com/users/poorkane/portfolio
                @SerializedName("self")
                val self: String? = "" // https://api.unsplash.com/users/poorkane
            ) : Parcelable
    
            @Keep
            @Parcelize
            data class ProfileImage(
                @SerializedName("large")
                val large: String? = "", // https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128
                @SerializedName("medium")
                val medium: String? = "", // https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=64&w=64
                @SerializedName("small")
                val small: String? = "" // https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32
            ) : Parcelable
        }
    }
}

fun GetPhotosRes.toDomainModel(): List<PhotoModel> {
    val urlList = mutableListOf<PhotoModel>()
    this.forEach {
        urlList.add(
            PhotoModel(
                full = it.urls?.full,
                raw = it.urls?.raw,
                regular = it.urls?.regular,
                small = it.urls?.small,
                thumb = it.urls?.thumb
            )
        )
    }
    return urlList
}