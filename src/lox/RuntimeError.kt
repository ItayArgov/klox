package com.klox

import java.lang.RuntimeException

class RuntimeError(token: Token, message: String) : RuntimeException(message) {
    final val token = token
}