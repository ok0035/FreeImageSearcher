package com.zerosword.domain.model


data class PhotoModel(
    val full: String? = "", // https://images.unsplash.com/face-springmorning.jpg?q=75&fm=jpg
    val raw: String? = "", // https://images.unsplash.com/face-springmorning.jpg
    val regular: String? = "", // https://images.unsplash.com/face-springmorning.jpg?q=75&fm=jpg&w=1080&fit=max
    val small: String? = "", // https://images.unsplash.com/face-springmorning.jpg?q=75&fm=jpg&w=400&fit=max
    val thumb: String? = "" // https://images.unsplash.com/face-springmorning.jpg?q=75&fm=jpg&w=200&fit=max
)