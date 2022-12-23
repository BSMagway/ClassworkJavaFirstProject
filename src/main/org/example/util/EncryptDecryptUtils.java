package org.example.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.example.util.AppConstants.SECRET_APP_KEY_1;
import static org.example.util.AppConstants.SECRET_APP_KEY_2;

public class EncryptDecryptUtils {
    private static IvParameterSpec ivParameterSpec;
    private static SecretKeySpec secretKeySpec;
    private static Cipher cipher;

    static {
        try {
            ivParameterSpec = new IvParameterSpec(SECRET_APP_KEY_1.getBytes("UTF-8"));
            secretKeySpec = new SecretKeySpec(SECRET_APP_KEY_2.getBytes("UTF-8"), "AES");
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Encrypt the string with this internal algorithm.
     *
     * @param toBeEncrypt string object to be encrypt.
     * @return returns encrypted string.
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String encrypt(String toBeEncrypt) {
        byte[] encrypted  = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            encrypted = cipher.doFinal(toBeEncrypt.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Base64.encodeBase64String(encrypted);
    }

    /**
     * Decrypt this string with the internal algorithm. The passed argument should be encrypted using
     * {@link #encrypt(String) encrypt} method of this class.
     *
     * @param encrypted encrypted string that was encrypted using {@link #encrypt(String) encrypt} method.
     * @return decrypted string.
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String decrypt(String encrypted)  {
        byte[] decryptedBytes = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            decryptedBytes = cipher.doFinal(Base64.decodeBase64(encrypted));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {

        System.out.println(encrypt("123"));

    }
}
