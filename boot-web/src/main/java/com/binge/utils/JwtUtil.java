//package com.binge.utils;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTCreationException;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.Claim;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.binge.exception.MyException;
//import com.binge.webentity.AxiosResult;
//import com.binge.webentity.AxiosStatus;
//import lombok.extern.slf4j.Slf4j;
//
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * 佛祖保佑  永无BUG
// *
// * @author: HuangJiaBin
// * @date: 2020年 12月21日
// * @description:
// **/
//@Slf4j
//public class JwtUtil {
//    /**
//     * 根据uuid创建jwt
//     *
//     * @param uuid
//     * @return jwt
//     */
//    public static String createToken(String uuid) {
//        String token = null;
//        try {
//            Algorithm algorithm = Algorithm.HMAC256("secret");
//            token = JWT.create()
//                    .withIssuer("auth0")
//                    .withClaim("uuid", uuid)
//                    .sign(algorithm);
//        } catch (JWTCreationException exception) {
//            //Invalid Signing configuration / Couldn't convert Claims.
//        }
//        return token;
//    }
//
//    /**
//     * 解析token
//     *
//     * @param token
//     * @return DecodedJWT 包含uuid的claim
//     */
//    public static DecodedJWT verifyToken(String token) {
//        DecodedJWT jwt = null;
//        try {
//            Algorithm algorithm = Algorithm.HMAC256("secret");
//            JWTVerifier verifier = JWT.require(algorithm)
//                    .withIssuer("auth0")
//                    .build(); //Reusable verifier instance
//            jwt = verifier.verify(token);
//        } catch (JWTVerificationException exception) {
//            log.error("JWT解析出现异常");
//            throw new MyException(AxiosResult.error(AxiosStatus.JWT_ERROR));
//        }
//        return jwt;
//    }
//
//
//    public static void main(String[] args) {
//        String uuid = UUID.randomUUID().toString();
//        System.out.println(uuid);
//        String token = JwtUtil.createToken(uuid);
//        DecodedJWT jwt = JwtUtil.verifyToken(token);
//        System.out.println(jwt.getClaim("uuid").asString());
//    }
//}
