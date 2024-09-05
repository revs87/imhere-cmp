package com.rvcoding.imhere.routes

import com.rvcoding.imhere.domain.Route
import com.rvcoding.imhere.domain.data.api.request.SubscribeRequest
import com.rvcoding.imhere.domain.data.api.request.UnsubscribeRequest
import com.rvcoding.imhere.domain.data.api.response.ApiResponse
import com.rvcoding.imhere.domain.data.api.response.ApiResult.Failure
import com.rvcoding.imhere.domain.data.api.response.ApiResult.Success
import com.rvcoding.imhere.domain.data.api.response.SubscriptionsResponse
import com.rvcoding.imhere.domain.data.db.SubscriptionEntity
import com.rvcoding.imhere.domain.repository.ApiSubscriptionRepository
import com.rvcoding.imhere.domain.repository.ApiUserRepository
import io.ktor.http.ContentType
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.get


fun Routing.subscriptions() {
    val userRepository: ApiUserRepository = get<ApiUserRepository>()
    val subscriptionRepository: ApiSubscriptionRepository = get<ApiSubscriptionRepository>()
    val json = Json { ignoreUnknownKeys = true }

    get(Route.Subscriptions.endpoint) {
        val subscriptions = subscriptionRepository.getAllSubscriptions()
        call.respondText(json.encodeToString(SubscriptionsResponse(subscriptions)), ContentType.Application.Json)
    }

    get(Route.UserSubscriptions.endpoint) {
        val userId = call.request.queryParameters["userId"] ?: ""
        val containsUser = userRepository.get(userId) != null
        when {
            userId.isBlank() -> {
                call.respondText(json.encodeToString(ApiResponse(Failure("Invalid request"))), ContentType.Application.Json)
                return@get
            }
            !containsUser -> {
                call.respondText(json.encodeToString(ApiResponse(Failure("User not found"))), ContentType.Application.Json)
                return@get
            }
        }
        val subscriptions = subscriptionRepository.getUserSubscriptions(userId)
        call.respondText(json.encodeToString(SubscriptionsResponse(subscriptions)), ContentType.Application.Json)
    }

    get(Route.UserSubscribers.endpoint) {
        val userId = call.request.queryParameters["userId"] ?: ""
        val containsUser = userRepository.get(userId) != null
        when {
            userId.isBlank() -> {
                call.respondText(json.encodeToString(ApiResponse(Failure("Invalid request"))), ContentType.Application.Json)
                return@get
            }
            !containsUser -> {
                call.respondText(json.encodeToString(ApiResponse(Failure("User not found"))), ContentType.Application.Json)
                return@get
            }
        }
        val subscriptions = subscriptionRepository.getUserSubscribers(userId)
        call.respondText(Json.encodeToString(SubscriptionsResponse(subscriptions)), ContentType.Application.Json)
    }

    post(Route.Subscribe.endpoint) {
        try {
            val request = call.receive<SubscribeRequest>()
            val containsUser = userRepository.get(request.userId) != null
            when {
                request.userId.isBlank() || request.userIdToSubscribe.isBlank() -> {
                    call.respondText(json.encodeToString(ApiResponse(Failure("Invalid request"))), ContentType.Application.Json)
                    return@post
                }
                !containsUser -> {
                    call.respondText(json.encodeToString(ApiResponse(Failure("User not found"))), ContentType.Application.Json)
                    return@post
                }
                request.userId == request.userIdToSubscribe -> {
                    call.respondText(json.encodeToString(ApiResponse(Failure("You can't subscribe to yourself"))), ContentType.Application.Json)
                    return@post
                }
            }
            val subscription = SubscriptionEntity(request.userId, request.userIdToSubscribe)
            subscriptionRepository.subscribe(subscription)
            userRepository.updateLastActivity(request.userId)
            call.respondText(json.encodeToString(ApiResponse(Success)), ContentType.Application.Json)
        } catch (e: Exception) {
            call.respondText(json.encodeToString(ApiResponse(Failure("${e.message}"))), ContentType.Application.Json)
        }
    }

    post(Route.Unsubscribe.endpoint) {
        try {
            val request = call.receive<UnsubscribeRequest>()
            val containsSubscription = subscriptionRepository.getUserSubscriptions(request.userId).find { it.userSubscribedId == request.userIdToUnsubscribe } != null
            when {
                request.userId.isBlank() || request.userIdToUnsubscribe.isBlank() -> {
                    call.respondText(json.encodeToString(ApiResponse(Failure("Invalid request"))), ContentType.Application.Json)
                    return@post
                }
                !containsSubscription -> {
                    call.respondText(json.encodeToString(ApiResponse(Failure("Subscription not found"))), ContentType.Application.Json)
                    return@post
                }
                request.userId == request.userIdToUnsubscribe -> {
                    call.respondText(json.encodeToString(ApiResponse(Failure("You can't unsubscribe yourself"))), ContentType.Application.Json)
                    return@post
                }
            }
            subscriptionRepository.unsubscribe(request.userId, request.userIdToUnsubscribe)
            userRepository.updateLastActivity(request.userId)
            call.respondText(json.encodeToString(ApiResponse(Success)), ContentType.Application.Json)
        } catch (e: Exception) {
            call.respondText(json.encodeToString(ApiResponse(Failure("${e.message}"))), ContentType.Application.Json)
        }
    }

}
