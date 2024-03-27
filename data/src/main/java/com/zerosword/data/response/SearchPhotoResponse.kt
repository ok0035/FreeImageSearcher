package com.zerosword.data.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.zerosword.domain.model.PhotoModel

@Keep
@Parcelize
data class SearchPhotoResponse(
    @SerializedName("results")
    val results: List<Result?>? = listOf(),
    @SerializedName("total")
    val total: Int? = 0, // 133
    @SerializedName("total_pages")
    val totalPages: Int? = 0 // 7
) : Parcelable {
    @Keep
    @Parcelize
    data class Result(
        @SerializedName("blur_hash")
        val blurHash: String? = "", // LaLXMa9Fx[D%~q%MtQM|kDRjtRIU
        @SerializedName("color")
        val color: String? = "", // #A7A2A1
        @SerializedName("created_at")
        val createdAt: String? = "", // 2014-11-18T14:35:36-05:00
        @SerializedName("description")
        val description: String? = "", // A man drinking a coffee.
        @SerializedName("height")
        val height: Int? = 0, // 3000
        @SerializedName("id")
        val id: String? = "", // eOLpJytrbsQ
        @SerializedName("liked_by_user")
        val likedByUser: Boolean? = false, // false
        @SerializedName("likes")
        val likes: Int? = 0, // 286
        @SerializedName("links")
        val links: Links? = Links(),
        @SerializedName("urls")
        val urls: Urls? = Urls(),
        @SerializedName("user")
        val user: User? = User(),
        @SerializedName("width")
        val width: Int? = 0 // 4000
    ) : Parcelable {
        @Keep
        @Parcelize
        data class Links(
            @SerializedName("download")
            val download: String? = "", // http://unsplash.com/photos/eOLpJytrbsQ/download
            @SerializedName("html")
            val html: String? = "", // http://unsplash.com/photos/eOLpJytrbsQ
            @SerializedName("self")
            val self: String? = "" // https://api.unsplash.com/photos/eOLpJytrbsQ
        ) : Parcelable

        @Keep
        @Parcelize
        data class Urls(
            @SerializedName("full")
            val full: String? = "", // https://hd.unsplash.com/photo-1416339306562-f3d12fefd36f
            @SerializedName("raw")
            val raw: String? = "", // https://images.unsplash.com/photo-1416339306562-f3d12fefd36f
            @SerializedName("regular")
            val regular: String? = "", // https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=92f3e02f63678acc8416d044e189f515
            @SerializedName("small")
            val small: String? = "", // https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=263af33585f9d32af39d165b000845eb
            @SerializedName("thumb")
            val thumb: String? = "" // https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=8aae34cf35df31a592f0bef16e6342ef
        ) : Parcelable

        @Keep
        @Parcelize
        data class User(
            @SerializedName("first_name")
            val firstName: String? = "", // Jeff
            @SerializedName("id")
            val id: String? = "", // Ul0QVz12Goo
            @SerializedName("instagram_username")
            val instagramUsername: String? = "", // instantgrammer
            @SerializedName("last_name")
            val lastName: String? = "", // Sheldon
            @SerializedName("links")
            val links: Links? = Links(),
            @SerializedName("name")
            val name: String? = "", // Jeff Sheldon
            @SerializedName("portfolio_url")
            val portfolioUrl: String? = "", // http://ugmonk.com/
            @SerializedName("profile_image")
            val profileImage: ProfileImage? = ProfileImage(),
            @SerializedName("twitter_username")
            val twitterUsername: String? = "", // ugmonk
            @SerializedName("username")
            val username: String? = "" // ugmonk
        ) : Parcelable {
            @Keep
            @Parcelize
            data class Links(
                @SerializedName("html")
                val html: String? = "", // http://unsplash.com/@ugmonk
                @SerializedName("likes")
                val likes: String? = "", // https://api.unsplash.com/users/ugmonk/likes
                @SerializedName("photos")
                val photos: String? = "", // https://api.unsplash.com/users/ugmonk/photos
                @SerializedName("self")
                val self: String? = "" // https://api.unsplash.com/users/ugmonk
            ) : Parcelable

            @Keep
            @Parcelize
            data class ProfileImage(
                @SerializedName("large")
                val large: String? = "", // https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=32085a077889586df88bfbe406692202
                @SerializedName("medium")
                val medium: String? = "", // https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=5a9dc749c43ce5bd60870b129a40902f
                @SerializedName("small")
                val small: String? = "" // https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=7cfe3b93750cb0c93e2f7caec08b5a41
            ) : Parcelable
        }
    }
}

fun SearchPhotoResponse.toDomainModel(): List<PhotoModel> {
    val photos = mutableListOf<PhotoModel>()
    this.results?.forEach {
        val urls = it?.urls
        urls?.let {
            photos.add(
                PhotoModel(
                    full = urls.full,
                    raw = urls.raw,
                    regular = urls.regular,
                    small = urls.small,
                    thumb = urls.thumb
                )
            )
        }
    }
    return photos
}