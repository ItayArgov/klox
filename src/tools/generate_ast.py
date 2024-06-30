import argparse

types = {"Binary": ["left: Expr", "operator: Token", "right: Expr"],
         "Grouping": ["expression: Expr"],
         "Literal": ["value: Any?"],
         "Unary": ["operator: Token", "right: Expr"]}

baseName = "Expr"

res = ["package com.klox", ""]
res += [f"abstract class {baseName}" + " {"]

res += ["interface Visitor<R> {"]
for typeName in types.keys():
    res += [f'fun visit{typeName}{baseName}({baseName.lower()}: {typeName}): R']

res += ['}']

res += ['abstract fun <R> accept(visitor: Visitor<R>): R']

for key, val in types.items():
    new_val = ["val " + word for word in val]
    args = ",".join(val)
    res += [f'class {key}({args}): {baseName}()' + " {"]
    res += ['override fun <R> accept(visitor: Visitor<R>): R {',
            f'return visitor.visit{key}{baseName}(this)', '}', '}']

res += '}'

print("\n".join(res))

# with open("Expr.kt", 'w') as f:
#     f.write(res)
