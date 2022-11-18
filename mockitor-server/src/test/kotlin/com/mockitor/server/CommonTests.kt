package com.mockitor.server

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.Option
import mu.KotlinLogging
import org.junit.jupiter.api.Test

class CommonTests {
    private val log = KotlinLogging.logger {}
    private val data = """
            {
    "id" : "6d8175db-89d1-465b-acce-4cdfe96e502c",
    "request" : {
      "urlPattern" : "http://localhost:8084/.*",
      "method" : "ANY",
      "headers": {
      "xxx": "xxx",
      "mockito-dependency":{"equalTo":"party-ms1"}
      }
      
    },
    "response" : {
      "status" : 200,
      "proxyBaseUrl" : "http://localhost:8084"
    },
    "uuid" : "6d8175db-89d1-465b-acce-4cdfe96e502c"
  }
        """.trimIndent()
    val patch = """
            {
    "request" : {
      "headers" : {
      "mockito-dependency" : { "equalTo": "party-ms" },
      "mockito-app" : { "equalTo": "od-ms" }
      }
    }
  }
        """.trimIndent()

    @Test
    fun testHeaders() {
        "{\"request\":{\"urlPattern\":\"http://localhost:8084/.*\",\"method\":\"POST\",\"headers\":{\"Connection\":{\"equalTo\":\"xxx\"}}},\"response\":{\"status\":200,\"proxyBaseUrl\":\"http://localhost:8084\"}}"
        val mapper = ObjectMapper()
        val p = JsonMergePatch.fromJson(mapper.readTree(patch))
        val o = mapper.readTree(data)
        val r = p.apply(o)
        log.info { r }

//        val jsonData = JsonPatch.fromJson(JsonLoader.fromString(data))
//        val json = JsonLoader.fromString(patch).apply(jsonData).toString()
//        log.info { json }
    }

    @Test
    fun testHeaders2() {
        val conf: Configuration = Configuration.builder()
            .options(Option.DEFAULT_PATH_LEAF_TO_NULL, Option.ALWAYS_RETURN_LIST).build()
        val json = JsonPath.using(conf).parse(data)
        var headers: headerType =
            JsonPath.using(conf)
                .parse(data)
                .read("$.request.headers", MutableList::class.java)
                    as headerType
        //headers.addAll(listOf(mutableMapOf("mockitor-dependency" to mutableMapOf("equalTo" to "party"))))
        //?: listOf(mutableMapOf("mockitor-dependency" to "party"), mutableMapOf("mockitor-app" to "1"))
        //headers.get(0).apply { mutableMapOf("mockitor-dependency" to "party") }
        var newHeaders = when {
            headers.size == 1 && headers.get(0).isNullOrEmpty() -> listOf(
                mutableMapOf("mockitor-dependency" to "party"),
                mutableMapOf("mockitor-app" to "1")
            ) as headerType
            else -> headers
        }

        //.getOrDefault("headers", hashMapOf("mockitor" to " none"))
        log.info { newHeaders }

        log.info {
            JsonPath.using(conf).parse(data).set("$.request.headers", newHeaders).jsonString()
        }
    }
}

typealias headerType = MutableList<Map<String, Map<String, String>>>

fun headerType.addIfNotExist() {

}