package com.klox

import com.klox.TokenType.*
import java.lang.RuntimeException

class Parser(tokens: ArrayList<Token>) {
    companion object {
        private class ParseError : RuntimeException()
    }

    private val tokens = tokens
    private var current = 0

    fun parse(): Expr? {
        return try {
            expression()
        } catch (error: ParseError) {
            null
        }
    }

    private fun expression(): Expr {
        return equality()
    }

    private fun equality(): Expr {
        val expr = comparison()
        while (match(BANG_EQUAL, EQUAL_EQUAL)) {
            val operator = previous()
            val right = comparison()
            val expr = Expr.Binary(expr, operator, right)
        }
        return expr
    }

    private fun comparison(): Expr {
        val expr = term()
        while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            val operator = previous()
            val right = term()
            val expr = Expr.Binary(expr, operator, right)
        }
        return expr
    }

    private fun term(): Expr {
        val expr = factor()
        while (match(MINUS, PLUS)) {
            val operator = previous()
            val right = factor()
            val expr = Expr.Binary(expr, operator, right)
        }
        return expr
    }

    private fun factor(): Expr {
        val expr = unary()
        while (match(SLASH, STAR)) {
            val operator = previous()
            val right = unary()
            val expr = Expr.Binary(expr, operator, right)
        }
        return expr
    }

    private fun unary(): Expr {
        if (match(BANG, MINUS)) {
            val operator = previous()
            val right = unary()
            return Expr.Unary(operator, right)
        }

        return primary()
    }

    private fun primary(): Expr {
        when {
            match(FALSE) -> return Expr.Literal(false)
            match(TRUE) -> return Expr.Literal(true)
            match(NIL) -> return Expr.Literal(null)
            match(NUMBER, STRING) -> return Expr.Literal(previous().literal)
            match(LEFT_PAREN) -> {
                val expr = expression()
                consume(RIGHT_PAREN, "Expect ')' after expression.")
                return Expr.Grouping(expr)
            }

            else -> throw error(peek(), "Expect expression.")
        }
    }

    private fun consume(type: TokenType, message: String): Token {
        if (check(type)) return advance()

        throw error(peek(), message)
    }

    private fun error(token: Token, message: String): ParseError {
        Klox.error(token, message)
        return ParseError()
    }

    private fun synchronize() {
        advance()
        while (!isAtEnd()) {
            if (previous().type == SEMICOLON) return
        }

        when (peek().type) {
            CLASS -> {}
            FOR -> {}
            FUN -> {}
            IF -> {}
            PRINT -> {}
            RETURN -> {}
            VAR -> {}
            WHILE -> {}
            else -> return
        }

        advance()
    }

    private fun match(vararg types: TokenType): Boolean {
        for (type in types) {
            if (check(type)) {
                advance()
                return true
            }
        }
        return false
    }

    private fun check(type: TokenType): Boolean {
        if (isAtEnd()) return false
        return peek().type == type
    }

    private fun advance(): Token {
        if (!isAtEnd()) current++
        return previous()
    }

    private fun isAtEnd(): Boolean {
        return peek().type == EOF
    }

    private fun peek(): Token {
        return tokens[current]
    }

    private fun previous(): Token {
        return tokens[current - 1]
    }
}