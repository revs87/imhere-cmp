package com.rvcoding.imhere.domain

import com.rvcoding.imhere.domain.Configuration.Companion.APP_ID as id

sealed class Route(val endpoint: String) {
    data object Root: Route("/")
    data object Configuration: Route("/$id/configuration")
    data object Register: Route("/$id/register")
    data object Login: Route("/$id/login")
    data object LoginInternal: Route("/$id/loginInternal")
    data object Users: Route("/$id/users")
    data object Sessions: Route("/$id/sessions")
    data object Subscriptions: Route("/$id/subscriptions")
    data object UserSubscriptions: Route("/$id/subscriptionsFromUser")
    data object UserSubscribedUsers: Route("/$id/userSubscribedUsers")
    data object UserSubscribers: Route("/$id/subscribersOfUser")
    data object Subscribe: Route("/$id/subscribe")
    data object Unsubscribe: Route("/$id/unsubscribe")
    data object State: Route("/$id/state")
    data object Sync: Route("/$id/sync")
}