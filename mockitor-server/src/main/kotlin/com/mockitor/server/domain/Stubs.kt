package com.mockitor.server.domain

import com.github.tomakehurst.wiremock.http.RequestMethod


data class DependencyStub(var request: Request, var response: Response) {

    companion object Factory {
        fun fromTriple(t: Triple<String, String, String>) = from(t.first, t.second, t.third)
        fun from(appName: String, depName: String, url: String) = DependencyStub(
            Request(
                "$url/.*",
                RequestMethod.ANY.value(),
                hashMapOf(
                    "mockitor-app" to equalTo(appName),
                    "mockitor-dependency" to equalTo(depName)
                )
            ), Response(url)
        )

        private fun equalTo(param: String) = hashMapOf("equalTo" to param)
    }

}

data class Request(
    var urlPattern: String,
    var method: String,
    var headers: Map<String, Map<String, String>>
)

data class Response(var proxyBaseUrl: String)

class StubId(val id: String)

data class StubSummary(
    val appName: String,
    val depName: String,
    val url: String,
    val data: String
)