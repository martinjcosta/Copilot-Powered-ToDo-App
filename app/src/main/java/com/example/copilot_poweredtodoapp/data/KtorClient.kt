package com.example.copilot_poweredtodoapp.data

import io.ktor.client.*
import io.ktor.client.engine.cio.*

val client = HttpClient(CIO) {
    expectSuccess = false
}