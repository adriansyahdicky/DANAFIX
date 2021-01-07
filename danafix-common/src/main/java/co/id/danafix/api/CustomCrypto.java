package co.id.danafix.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import static co.id.danafix.constant.ConstantVariable.*;

public class CustomCrypto {

    private static final Logger log = LoggerFactory.getLogger(CustomCrypto.class);

    private static final boolean IS_ENCRYPTING = true;

    private static String md5(final String input) throws NoSuchAlgorithmException{
        final MessageDigest md = MessageDigest.getInstance("MD5");
        final byte[] messageDigest = md.digest(input.getBytes());
        final BigInteger number = new BigInteger(1, messageDigest);
        return String.format("%032x", number);
    }

    private Cipher initCipher(final int mode, final String initialVectorString, final String secretKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException {

        final SecretKeySpec secretKeySpec = new SecretKeySpec(md5(secretKey).getBytes(), "AES");
        final IvParameterSpec initialVector = new IvParameterSpec(initialVectorString.getBytes());
        final Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
        cipher.init(mode, secretKeySpec, initialVector);
        return cipher;
    }

    public String encrypt(final String plainTextToEncrypt, String initialVector, final String secretKey){
        String encryptData = null;
        try{
            final Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, initialVector, secretKey);
            final byte[] encryptedByArray = cipher.doFinal(plainTextToEncrypt.getBytes());
            /** encode using base64 **/
            // USING JAVA 8 encryptData = (new BASE64Encoder()).encode(encryptedByArray);
            /** encode using java 11 **/
            encryptData = Base64.getMimeEncoder().encodeToString(encryptedByArray);
        }catch (Exception e){
            log.error("{\"response\" : "+e.getMessage()+"}");
            e.getMessage();
        }

        if (log.isInfoEnabled()){
            log.debug("{\"response\" : "+encryptData+"}");
        }
        return encryptData;
    }

    public String encryptPasswordUser(String password) {
            return this.encrypt(password, IV, SECRET);
    }

    public String decryptPasswordUser(String cipherPassword) {
            return this.decrypt(cipherPassword, IV, SECRET);
    }

    public String decrypt(final String encryptedText, final String initialVector, final String secretKey){
        String plainText = null;
        try{
            final Cipher cipher = initCipher(Cipher.DECRYPT_MODE, initialVector, secretKey);
            //final byte[] encryptedByteArray = (new BASE64Decoder()).decodeBuffer(encryptedText);
            final byte[] encryptedByteArray = Base64.getMimeDecoder().decode(encryptedText);
            /** decrypted data **/
            final byte[] decryptedByteArray = cipher.doFinal(encryptedByteArray);
            plainText = new String(decryptedByteArray, StandardCharsets.UTF_8);
        }catch (Exception e){
            log.error("{\"response\" : "+e.getMessage()+"}");
            e.getMessage();
        }

        if (log.isInfoEnabled()){
            log.debug("{\"response\" : "+plainText+"}");
        }

        return plainText;
    }

}
