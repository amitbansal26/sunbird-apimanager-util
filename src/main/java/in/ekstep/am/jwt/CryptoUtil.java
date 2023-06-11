package in.ekstep.am.jwt;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.*;

public class CryptoUtil {
    private static final Charset US_ASCII = Charset.forName("US-ASCII");

    public static byte[] generateHMAC(String payLoad, String secretKey, String algorithm) {
        Mac mac;
        byte[] signature;
        try {
            mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(secretKey.getBytes(), algorithm));
            signature = mac.doFinal(payLoad.getBytes(US_ASCII));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return null;
        }
        return signature;
    }

    public static byte[] generateHMAC(String payLoad, byte[] secretKey, String algorithm) {
        Mac mac;
        byte[] signature;
        try {
            mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(secretKey, algorithm));
            signature = mac.doFinal(payLoad.getBytes(US_ASCII));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return null;
        }
        return signature;
    }

    public static byte[] generateRSASign(String payLoad, PrivateKey key, String algorithm) {
        Signature sign;
        byte[] signature;
        try {
            sign = Signature.getInstance(algorithm);
            sign.initSign(key);
            sign.update(payLoad.getBytes(US_ASCII));
            signature = sign.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            return null;
        }
        return signature;
    }

    public static boolean verifyRSASign(String payLoad, byte[] signature, PublicKey key, String algorithm) {
        Signature sign;
        try {
            sign = Signature.getInstance(algorithm);
            System.out.println("*****************SIGNATURE Algo *************" + sign.getAlgorithm());
            System.out.println("*****************SIGNATURE Parameter Algo *************" + sign.getParameters().getAlgorithm());
            System.out.println("********************  Signature Provider " + sign.getProvider().getName());
            System.out.println("**********Public key inside verifyRSASign ****" + key);
            System.out.println("**********Public key encoded inside verifyRSASign ****" + key.getEncoded());
            System.out.println("**********Public key toString inside verifyRSASign ****" + key.toString());
            System.out.println("**********Payload inside verifyRSASign ****" + payLoad);
            sign.initVerify(key);
            sign.update(payLoad.getBytes(US_ASCII));
            return sign.verify(signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            return false;
        }
    }
}
