package com.rvcoding.imhere.domain

import com.rvcoding.imhere.domain.Configuration.Companion.APP_ID as id

sealed class Route(val path: String) {
    data object Root: Route("/")
    data object Configuration: Route("/$id/configuration")
    data object Register: Route("/$id/register")
    data object Login: Route("/$id/login")
    data object LoginInternal: Route("/$id/loginInternal")
    data object Users: Route("/$id/users")
    data object Subscriptions: Route("/$id/subscriptions")
    data object UserSubscriptions: Route("/$id/subscriptionsFromUser")
    data object UserSubscribers: Route("/$id/subscribersOfUser")
    data object Subscribe: Route("/$id/subscribe")
    data object Unsubscribe: Route("/$id/unsubscribe")
    data object State: Route("/$id/state")
    data object Sync: Route("/$id/sync")
}