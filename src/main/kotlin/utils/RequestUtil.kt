package com.hcyacg.hanayamata.utils

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import net.mamoe.mirai.utils.MiraiLogger
import okhttp3.*
import java.util.concurrent.TimeUnit

/**
 * http请求
 */
object RequestUtil {

    private val client = OkHttpClient().newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS)
        .readTimeout(60000, TimeUnit.MILLISECONDS)
    private lateinit var response: Response

    fun requestObject(
        method: Method,
        uri: String,
        body: RequestBody?,
        headers: Headers,
        logger: MiraiLogger
    ): JSONObject? {
        /**
         * 进行请求转发
         */
        when (method) {
            Method.GET -> {
                return httpObject(Request.Builder().url(uri).headers(headers).get().build(), logger)
            }
            Method.POST -> {
                return body?.let { Request.Builder().url(uri).headers(headers).post(it).build() }
                    ?.let { httpObject(it, logger) }
            }
            Method.PUT -> {
                return body?.let { Request.Builder().url(uri).headers(headers).put(it).build() }
                    ?.let { httpObject(it, logger) }
            }
            Method.DEL -> {
                return httpObject(Request.Builder().url(uri).headers(headers).delete(body).build(), logger)
            }
        }
    }

    fun requestArray(
        method: Method,
        uri: String,
        body: RequestBody?,
        headers: Headers,
        logger: MiraiLogger
    ): JSONArray? {
        /**
         * 进行请求转发
         */
        when (method) {
            Method.GET -> {
                return httpArray(Request.Builder().url(uri).headers(headers).get().build(), logger)
            }
            Method.POST -> {
                return body?.let { Request.Builder().url(uri).headers(headers).post(it).build() }
                    ?.let { httpArray(it, logger) }
            }
            Method.PUT -> {
                return body?.let { Request.Builder().url(uri).headers(headers).put(it).build() }
                    ?.let { httpArray(it, logger) }
            }
            Method.DEL -> {
                return httpArray(Request.Builder().url(uri).headers(headers).delete(body).build(), logger)
            }
        }

    }

    /**
     * 发送http请求，返回数据（其中根据proxy是否配置加入代理机制）
     */
    private fun httpObject(request: Request, logger: MiraiLogger): JSONObject? {


        response = client.build().newCall(request).execute()

        if (response.isSuccessful) {
            return JSONObject.parseObject(response.body?.string())
        }

        response.close()
        return null
    }

    private fun httpArray(request: Request, logger: MiraiLogger): JSONArray? {

        response = client.build().newCall(request).execute()

        if (response.isSuccessful) {
            return JSONArray.parseArray(response.body?.string())
        }
        response.close()
        return null
    }

}

/**
 * http的请求方式
 */
enum class Method {
    GET, POST, PUT, DEL
}