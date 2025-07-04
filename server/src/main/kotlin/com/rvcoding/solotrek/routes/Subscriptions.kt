package com.rvcoding.solotrek.routes

import com.rvcoding.solotrek.domain.Route
import com.rvcoding.solotrek.domain.data.api.request.SubscribeRequest
import com.rvcoding.solotrek.domain.data.api.request.UnsubscribeRequest
import com.rvcoding.solotrek.domain.data.api.response.ApiResponse
import com.rvcoding.solotrek.domain.data.api.response.ApiResult.Failure
import com.rvcoding.solotrek.domain.data.api.response.ApiResult.Success
import com.rvcoding.solotrek.domain.data.api.response.SubscriptionsResponse
import com.rvcoding.solotrek.domain.data.api.response.UsersResponse
import com.rvcoding.solotrek.domain.data.db.SubscriptionEntity
import com.rvcoding.solotrek.domain.data.db.UserEntity
import com.rvcoding.solotrek.domain.data.db.toExposed
import com.rvcoding.solotrek.domain.repository.ApiSubscriptionRepository
import com.rvcoding.solotrek.domain.repository.ApiUserRepository
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


fun Routing.subscriptions() {
    val userRepository: ApiUserRepository by com.rvcoding.solotrek.inject<ApiUserRepository>()
    val subscriptionRepository: ApiSubscriptionRepository by com.rvcoding.solotrek.inject<ApiSubscriptionRepository>()
    val json = Json { ignoreUnknownKeys = true }

    get(Route.Subscriptions.endpoint) {
        val subscriptions = subscriptionRepository.getAllSubscriptions()
        call.respondText(json.encodeToString(SubscriptionsResponse(subscriptions)), ContentType.Application.Json)
    }

    get(Route.UserSubscriptions.endpoint) {
        try {
            val userId = call.request.queryParameters["userId"] ?: ""
            val containsUser = userRepository.get(userId) != null
            when {
                userId.isBlank() -> {
                    call.respondText(
                        text = json.encodeToString(ApiResponse(Failure("Invalid request"))),
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.BadRequest
                    )
                    return@get
                }

                !containsUser -> {
                    call.respondText(
                        text = json.encodeToString(ApiResponse(Failure("User not found"))),
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.NotFound
                    )
                    return@get
                }
            }
            val subscriptions = subscriptionRepository.getUserSubscriptions(userId)
            call.respondText(
                json.encodeToString(SubscriptionsResponse(subscriptions)),
                ContentType.Application.Json
            )
        } catch (e: Exception) {
            call.respondText(
                text = json.encodeToString(ApiResponse(Failure("${e.message}"))),
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.InternalServerError
            )
        }
    }

    get(Route.UserSubscribedUsers.endpoint) {
        try {
            val userId = call.request.queryParameters["userId"] ?: ""
            val containsUser = userRepository.get(userId) != null
            when {
                userId.isBlank() -> {
                    call.respondText(
                        text = json.encodeToString(ApiResponse(Failure("Invalid request"))),
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.BadRequest
                    )
                    return@get
                }
                !containsUser -> {
                    call.respondText(
                        text = json.encodeToString(ApiResponse(Failure("User not found"))),
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.NotFound
                    )
                    return@get
                }
            }
            val subscriptions = subscriptionRepository.getUserSubscriptions(userId)
            val subscribedUsers = subscriptions.map { userRepository.get(it.userSubscribedId)?.toExposed() ?: UserEntity.Default.toExposed() }
            call.respondText(json.encodeToString(UsersResponse(subscribedUsers)), ContentType.Application.Json)
        } catch (e: Exception) {
            call.respondText(
                text = json.encodeToString(ApiResponse(Failure("${e.message}"))),
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.InternalServerError
            )
        }
    }

    get(Route.UserSubscribers.endpoint) {
        try {
            val userId = call.request.queryParameters["userId"] ?: ""
            val containsUser = userRepository.get(userId) != null
            when {
                userId.isBlank() -> {
                    call.respondText(
                        text = json.encodeToString(ApiResponse(Failure("Invalid request"))),
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.BadRequest
                    )
                    return@get
                }
                !containsUser -> {
                    call.respondText(
                        text = json.encodeToString(ApiResponse(Failure("User not found"))),
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.NotFound
                    )
                    return@get
                }
            }
            val subscriptions = subscriptionRepository.getUserSubscribers(userId)
            val userSubscribers = subscriptions.map { userRepository.get(it.userId)?.toExposed() ?: UserEntity.Default.toExposed() }
            call.respondText(Json.encodeToString(UsersResponse(userSubscribers)), ContentType.Application.Json)
        } catch (e: Exception) {
            call.respondText(
                text = json.encodeToString(ApiResponse(Failure("${e.message}"))),
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.InternalServerError
            )
        }
    }

    post(Route.Subscribe.endpoint) {
        try {
            val request = call.receive<SubscribeRequest>()
            val containsUser = userRepository.get(request.userId) != null
            when {
                request.userId.isBlank() || request.userIdToSubscribe.isBlank() -> {
                    call.respondText(
                        text = json.encodeToString(ApiResponse(Failure("Invalid request"))),
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.BadRequest
                    )
                    return@post
                }
                !containsUser -> {
                    call.respondText(
                        text = json.encodeToString(ApiResponse(Failure("User not found"))),
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.NotFound
                    )
                    return@post
                }
                request.userId == request.userIdToSubscribe -> {
                    call.respondText(
                        text = json.encodeToString(ApiResponse(Failure("You can't subscribe to yourself"))),
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.NotAcceptable
                    )
                    return@post
                }
            }
            val subscription = SubscriptionEntity(request.userId, request.userIdToSubscribe)
            subscriptionRepository.subscribe(subscription)
            userRepository.updateLastActivity(request.userId)
            call.respondText(json.encodeToString(ApiResponse(Success)), ContentType.Application.Json)
        } catch (e: Exception) {
            call.respondText(
                text = json.encodeToString(ApiResponse(Failure("${e.message}"))),
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.InternalServerError
            )
        }
    }

    post(Route.Unsubscribe.endpoint) {
        try {
            val request = call.receive<UnsubscribeRequest>()
            val containsSubscription = subscriptionRepository.getUserSubscriptions(request.userId).find { it.userSubscribedId == request.userIdToUnsubscribe } != null
            when {
                request.userId.isBlank() || request.userIdToUnsubscribe.isBlank() -> {
                    call.respondText(
                        text = json.encodeToString(ApiResponse(Failure("Invalid request"))),
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.BadRequest
                    )
                    return@post
                }
                !containsSubscription -> {
                    call.respondText(
                        text = json.encodeToString(ApiResponse(Failure("User not found"))),
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.NotFound
                    )
                    return@post
                }
                request.userId == request.userIdToUnsubscribe -> {
                    call.respondText(
                        text = json.encodeToString(ApiResponse(Failure("You can't subscribe to yourself"))),
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.NotAcceptable
                    )
                    return@post
                }
            }
            subscriptionRepository.unsubscribe(request.userId, request.userIdToUnsubscribe)
            userRepository.updateLastActivity(request.userId)
            call.respondText(json.encodeToString(ApiResponse(Success)), ContentType.Application.Json)
        } catch (e: Exception) {
            call.respondText(
                text = json.encodeToString(ApiResponse(Failure("${e.message}"))),
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.InternalServerError
            )
        }
    }

}
