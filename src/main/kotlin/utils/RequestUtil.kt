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
        val response: Response = client.build().newCall(request).execute()

        try {
            if (response.isSuccessful) {
                return JSONObject.parseObject(response.body?.string())
            }
            return null
        } catch (e: Exception) {
            logger.error(e)
            return null
        } finally {
            response.close()
        }
    }

    private fun httpArray(request: Request, logger: MiraiLogger): JSONArray? {
        val response: Response = client.build().newCall(request).execute()
        try {
            if (response.isSuccessful) {
                return JSONArray.parseArray(response.body?.string())
            }
            return null
        } catch (e: Exception) {
            logger.error(e)
            return null
        } finally {
            response.close()
        }
    }

}

/**
 * http的请求方式
 */
enum class Method {
    GET, POST, PUT, DEL
}