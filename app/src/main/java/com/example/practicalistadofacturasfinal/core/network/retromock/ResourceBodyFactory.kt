package com.example.practicalistadofacturasfinal.core.network.retromock

import co.infinum.retromock.BodyFactory
import java.io.IOException
import java.io.InputStream

class ResourceBodyFactory: BodyFactory {
    @Throws(IOException::class)
    override fun create(input: String): InputStream? {
        return ResourceBodyFactory::class.java.classLoader?.getResourceAsStream(input)
    }
}