# mathasaservice

Clojure, ring, compojure, instaparse demo project.

## API Description

### GET /calculus?query=[input]
Input: base64 encoded string containing an arithmetic expession.
Supported operators: + - * / ( ).

Return (JSON):
 - Success: { error: false, result: number }
 - Error: { error: true, message: string }

