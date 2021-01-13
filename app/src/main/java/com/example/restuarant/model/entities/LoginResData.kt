package com.example.restuarant.model.entities

data class LoginResData(
         var headers: Any,
         var body: BodyData,
         var statusCodeValue: Int,
         var statusCode: String = ""
)

data class BodyData(
         var accessToken: String = "",
         var tokenType: String = ""
)

//{
//    "headers": {},
//    "body": {
//        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0OTNlYmIwOS05ZTRhLTQ0MDQtYTY0YS1iYWYxNDE5Y2NkY2YiLCJpYXQiOjE2MTA0NTM2NDEsImV4cCI6MTYxMjE4MTY0MX0.DtzBaHFIkCW9up1Sb-W0Ekl7s3Lc8NAlbnr3Z1c_-E4njCv7xqlpZ6BBoHLrUGrmn0a5kt1OQbkQKNO2cjI8kg",
//        "tokenType": "Bearer"
//    },
//    "statusCodeValue": 200,
//    "statusCode": "OK"
//}