package com.klox

import java.io.InputStreamReader
import java.nio.charset.Charset
import kotlin.system.exitProcess
import java.nio.file.Files
import java.nio.file.Paths

class Klox {
    companion object {
        private val interpreter: Interpreter = Interpreter()
        private var hadError: Boolean = false
        private var hadRuntimeError: Boolean = true
        private fun runFile(path: String) {
            val bytes = Files.readAllBytes(Paths.get(path))
            run(String(bytes, Charset.defaultCharset()))

            if (hadError) exitProcess(65)
            if (hadRuntimeError) exitProcess(70)
        }

        private fun runPrompt() {
            while (true) {
                print("> ")
                val line = readlnOrNull() ?: break
                run(line)
                hadError = false
            }
        }

        private fun run(source: String) {
            val scanner = Scanner(source)
            val tokens = scanner.scanTokens()
            val parser = Parser(tokens)
            val expression = parser.parse()

            if (hadError) return

            interpreter.interpret(expression!!)
        }

        fun error(line: Int, message: String) {
            report(line, "", message)
        }

        fun error(token: Token, message: String) {
            if (token.type == TokenType.EOF) {
                report(token.line, " at end", message)
            } else {
                report(token.line, " at '${token.lexeme}'", message)
            }
        }

        fun runtimeError(error: RuntimeError) {
            System.err.println("${error.message}\n[line ${error.token.line}]")
            hadRuntimeError = true
        }

        private fun report(line: Int, where: String, message: String) {
            System.err.println("[line $line] Error $where: $message")
            hadError = true
        }
    }

    fun main(args: Array<String>) {
        if (args.size > 1) {
            println("Usage: klox [script]")
            exitProcess(64)
        } else if (args.size == 1) {
            runFile(args[0])
        } else {
            runPrompt()
        }
    }
}
