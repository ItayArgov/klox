# Klox – Interpreter for the Lox Programming Language

## Overview
Klox is an interpreter for a subset of the Lox programming language, developed in Kotlin. This project demonstrates the implementation of a programming language interpreter, including lexical analysis, parsing, and runtime evaluation.

## Table of Contents
- [Overview](#overview)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Design Patterns](#design-patterns)
- [Error Handling](#error-handling)
- [Contributing](#contributing)
- [License](#license)

## Installation
1. **Clone the repository:**
    ```bash
    git clone https://github.com/ItayArgov/klox.git
    cd klox
    ```

2. **Build the project:**
    Ensure you have Kotlin and Gradle installed, then run:
    ```bash
    gradle build
    ```

## Usage
1. **Run the interpreter:**
    ```bash
    gradle run
    ```

2. **Use the REPL Interface:**
    After running the interpreter, you can interactively execute Lox code.

## Project Structure
- **`Expr.kt`:** Defines the Abstract Syntax Tree (AST) classes and the Visitor interface.
- **`Interpreter.kt`:** Contains the logic for evaluating Lox expressions.
- **`Parser.kt`:** Parses the tokenized source code into an AST.
- **`Scanner.kt`:** Tokenizes the source code.
- **`Token.kt`:** Represents the tokens produced by the scanner.
- **`TokenType.kt`:** Enum class for different types of tokens.
- **`RuntimeError.kt`:** Defines runtime error handling.
- **`generate_ast.py`:** Python script for generating AST classes.
- **`keywords.py`:** Script for processing language keywords.

## Error Handling
The interpreter includes a robust error handling mechanism to capture and report runtime errors. This ensures a smooth user experience by providing meaningful feedback when something goes wrong.
