package com.practice.mviarchitecturedemo.util

import com.practice.mviarchitecturedemo.ui.model.BlogPost
import com.practice.mviarchitecturedemo.ui.model.User
import java.util.*

class TestUtil {

    companion object {
        val TEST_BLOG_LIST = Collections.unmodifiableList<BlogPost>(
            object : ArrayList<BlogPost>() {
                init {
                    add(
                        BlogPost(
                            1,
                            "Title 1",
                            " Lorem Ipsum ",
                            "https://cdn.open-api.xyz/open-api-static/static-blog-images/image1.jpg"
                        )
                    )
                    add(
                        BlogPost(
                            2,
                            "Title 2",
                            " Lorem Ipsum ",
                            "https://cdn.open-api.xyz/open-api-static/static-blog-images/image2.jpg"
                        )
                    )
                }
            }
        )

        val USER : User = User("test_user@yahoo.com","Test User",
            "https://cdn.open-api.xyz/open-api-static/static-blog-images/image6.jpg")
    }

}