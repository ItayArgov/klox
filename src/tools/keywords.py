tokens = '''
AND, CLASS, ELSE, FALSE, FUN, FOR, IF, NIL, OR,
PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE,
'''
keywords = tokens.replace(" ", "").replace("\n", "").split(",")
keywords.pop()
print(",\n".join([fr'"{token.lower()}" to {token}' for token in keywords]))
