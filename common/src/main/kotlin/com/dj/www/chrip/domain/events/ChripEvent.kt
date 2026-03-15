package com.dj.www.chrip.domain.events

import java.time.Instant

interface ChripEvent {
    val eventId: String
    val eventKey: String
    val occurredAt: Instant
    val exchange: String
}