package com.rvcoding.imhere.routes

import com.rvcoding.imhere.domain.Route
import com.rvcoding.imhere.domain.api.request.SubscribeRequest
import com.rvcoding.imhere.domain.api.request.UnsubscribeRequest
import com.rvcoding.imhere.domain.api.response.SubscriptionResponse
import com.rvcoding.imhere.domain.api.response.SubscriptionResult.Failure
import com.rvcoding.imhere.domain.api.response.SubscriptionResult.Success
import com.rvcoding.imhere.domain.api.response.SubscriptionsResponse
import com.rvcoding.imhere.domain.models.Subscription
import com.rvcoding.imhere.domain.repository.SubscriptionRepository
import com.rvcoding.imhere.domain.repository.UserRepository
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
    val userRepository: UserRepository = get<UserRepository>()
    val subscriptionRepository: SubscriptionRepository = get<SubscriptionRepository>()

    get(Route.Subscriptions.path) {
        val subscriptions = subscriptionRepository.getAllSubscriptions()
        call.respondText(Json.encodeToString(SubscriptionsResponse(subscriptions)), ContentType.Application.Json)
    }

    get(Route.UserSubscriptions.path) {
        val userId = call.request.queryParameters["userId"] ?: ""
        val containsUser = userRepository.get(userId) != null
        when {
            userId.isBlank() -> {
                call.respondText(Json.encodeToString(SubscriptionResponse(Failure("Invalid request"))), ContentType.Application.Json)
                return@get
            }
            !containsUser -> {
                call.respondText(Json.encodeToString(SubscriptionResponse(Failure("User not found"))), ContentType.Application.Json)
                return@get
            }
        }
        val subscriptions = subscriptionRepository.getUserSubscriptions(userId)
        call.respondText(Json.encodeToString(SubscriptionsResponse(subscriptions)), ContentType.Application.Json)
    }

    get(Route.UserSubscribers.path) {
        val userId = call.request.queryParameters["userId"] ?: ""
        val containsUser = userRepository.get(userId) != null
        when {
            userId.isBlank() -> {
                call.respondText(Json.encodeToString(SubscriptionResponse(Failure("Invalid request"))), ContentType.Application.Json)
                return@get
            }
            !containsUser -> {
                call.respondText(Json.encodeToString(SubscriptionResponse(Failure("User not found"))), ContentType.Application.Json)
                return@get
            }
        }
        val subscriptions = subscriptionRepository.getUserSubscribers(userId)
        call.respondText(Json.encodeToString(SubscriptionsResponse(subscriptions)), ContentType.Application.Json)
    }

    post(Route.Subscribe.path) {
        try {
            val request = call.receive<SubscribeRequest>()
            val containsUser = userRepository.get(request.userId) != null
            when {
                request.userId.isBlank() || request.userIdToSubscribe.isBlank() -> {
                    call.respondText(Json.encodeToString(SubscriptionResponse(Failure("Invalid request"))), ContentType.Application.Json)
                    return@post
                }
                !containsUser -> {
                    call.respondText(Json.encodeToString(SubscriptionResponse(Failure("User not found"))), ContentType.Application.Json)
                    return@post
                }
                request.userId == request.userIdToSubscribe -> {
                    call.respondText(Json.encodeToString(SubscriptionResponse(Failure("You can't subscribe to yourself"))), ContentType.Application.Json)
                    return@post
                }
            }
            val subscription = Subscription(request.userId, request.userIdToSubscribe)
            subscriptionRepository.subscribe(subscription)
            userRepository.updateLastActivity(request.userId)
            call.respondText(Json.encodeToString(SubscriptionResponse(Success)), ContentType.Application.Json)
        } catch (e: Exception) {
            call.respondText(Json.encodeToString(SubscriptionResponse(Failure("${e.message}"))), ContentType.Application.Json)
        }
    }

    post(Route.Unsubscribe.path) {
        try {
            val request = call.receive<UnsubscribeRequest>()
            val containsSubscription = subscriptionRepository.getUserSubscriptions(request.userId).find { it.userSubscribedId == request.userIdToUnsubscribe } != null
            when {
                request.userId.isBlank() || request.userIdToUnsubscribe.isBlank() -> {
                    call.respondText(Json.encodeToString(SubscriptionResponse(Failure("Invalid request"))), ContentType.Application.Json)
                    return@post
                }
                !containsSubscription -> {
                    call.respondText(Json.encodeToString(SubscriptionResponse(Failure("Subscription not found"))), ContentType.Application.Json)
                    return@post
                }
                request.userId == request.userIdToUnsubscribe -> {
                    call.respondText(Json.encodeToString(SubscriptionResponse(Failure("You can't unsubscribe yourself"))), ContentType.Application.Json)
                    return@post
                }
            }
            subscriptionRepository.unsubscribe(request.userId, request.userIdToUnsubscribe)
            userRepository.updateLastActivity(request.userId)
            call.respondText(Json.encodeToString(SubscriptionResponse(Success)), ContentType.Application.Json)
        } catch (e: Exception) {
            call.respondText(Json.encodeToString(SubscriptionResponse(Failure("${e.message}"))), ContentType.Application.Json)
        }
    }

}
