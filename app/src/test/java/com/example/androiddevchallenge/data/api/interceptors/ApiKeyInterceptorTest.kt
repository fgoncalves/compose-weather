package com.example.androiddevchallenge.data.api.interceptors

import com.example.androiddevchallenge.commons.API_KEY
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.junit.Test

class ApiKeyInterceptorTest {
    @Test
    fun `intercept adds api key`() {
        val interceptor = ApiKeyInterceptor()
        val request = Request.Builder()
            .url(
                HttpUrl.Builder()
                    .scheme("https")
                    .host("localhost.com")
                    .build()
            )
            .build()
        val chain = mock<Interceptor.Chain> {
            on {
                request()
            } doReturn request

            on {
                proceed(any())
            } doReturn Response.Builder()
                .code(200)
                .message("OK")
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .build()
        }

        interceptor.intercept(chain)

        argumentCaptor<Request>().run {
            verify(chain).proceed(capture())

            assertThat(firstValue.url.toString()).isEqualTo("https://localhost.com/?appid=$API_KEY")
        }
    }
}