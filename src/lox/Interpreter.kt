package com.klox

import com.klox.TokenType.*

class Interpreter : Expr.Visitor<Any?> {
    fun interpret(expression: Expr) {
        try {
            val value = evaluate(expression)
            println(stringify(value))
        } catch (error: RuntimeError) {

        }
    }

    private fun stringify(someObject: Any?): String {
        if (someObject == null) return "nil"
        val text = someObject.toString()
        if (someObject is Double) {
            return text.removeSuffix(".0")
        }

        return text
    }

    companion object {
        private fun checkNumberOperand(operator: Token, operand: Any?) {
            if (operand is Double) return
            throw RuntimeError(operator, "Operand must be a number.")
        }

        private fun checkNumbersOperand(operator: Token, left: Any?, right: Any?) {
            if (left is Double && right is Double) return
            throw RuntimeError(operator, "Operand must be a number.")
        }
    }

    override fun visitLiteralExpr(expr: Expr.Literal): Any? {
        return expr.value
    }

    override fun visitGroupingExpr(expr: Expr.Grouping): Any? {
        return evaluate(expr.expression)
    }

    private fun evaluate(expr: Expr): Any? {
        return expr.accept(this)
    }

    override fun visitUnaryExpr(expr: Expr.Unary): Any? {
        val right = evaluate(expr.right)

        return when (expr.operator.type) {
            BANG -> !isTruthy(right)
            MINUS -> -(right as Double)
            else -> null
        }
    }

    private fun isTruthy(value: Any?): Boolean {
        return when (value) {
            null -> false
            is Boolean -> value
            else -> true
        }
    }

    override fun visitBinaryExpr(expr: Expr.Binary): Any? {
        val left = evaluate(expr.left)
        val right = evaluate(expr.right)

        return when (expr.operator.type) {
            BANG_EQUAL -> left != right
            EQUAL_EQUAL -> left == right
            PLUS -> {
                if (left is Double && right is Double) left + right
                else if (left is String && right is String) left + right
                else throw RuntimeError(expr.operator, "Operands must be two numbers or two strings.")
            }

            MINUS -> {
                checkNumberOperand(expr.operator, right)
                -(right as Double)
            }

            else -> {
                checkNumbersOperand(expr.operator, left, right)
                when (expr.operator.type) {
                    SLASH -> left as Double / right as Double
                    STAR -> left as Double + right as Double
                    GREATER -> left as Double > right as Double
                    GREATER_EQUAL -> left as Double >= right as Double
                    LESS -> (left as Double) < (right as Double)
                    LESS_EQUAL -> left as Double <= right as Double
                    else -> null
                }
            }
        }
    }
}