package com.nearor.mylibrary.secure;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final String ALGORITHM_NAME = "AES";

	/** 
	 * 加密 
	 *  
	 * @param content 需要加密的内容 
	 * @param password  加密密码 
	 * @return 
	 */  
	public static byte[] encrypt(String content, String password) {  
	        try {             
	                /*KeyGenerator kgen = KeyGenerator.getInstance("AES");  
	                kgen.init(128, new SecureRandom(password.getBytes()));  
	                SecretKey secretKey = kgen.generateKey();  
	                byte[] enCodeFormat = secretKey.getEncoded();*/  
	                SecretKeySpec key = new SecretKeySpec(getKey(password), ALGORITHM_NAME);  
	                Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);// 创建密码器   
	                byte[] byteContent = content.getBytes("utf-8");  
	                cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化   
	                byte[] result = cipher.doFinal(byteContent);  
	                return result; // 加密   
	        } catch (NoSuchAlgorithmException e) {  
	                e.printStackTrace();  
	        } catch (NoSuchPaddingException e) {  
	                e.printStackTrace();  
	        } catch (InvalidKeyException e) {  
	                e.printStackTrace();  
	        } catch (UnsupportedEncodingException e) {  
	                e.printStackTrace();  
	        } catch (IllegalBlockSizeException e) {  
	                e.printStackTrace();  
	        } catch (BadPaddingException e) {  
	                e.printStackTrace();  
	        }  
	        return null;  
	}  
	
	/**解密 
	 * @param content  待解密内容 
	 * @param password 解密密钥 
	 * @return 
	 */  
	public static byte[] decrypt(byte[] content, String password) {  
	        try {  
	                 /*KeyGenerator kgen = KeyGenerator.getInstance("AES");  
	                 kgen.init(128, new SecureRandom(password.getBytes()));  
	                 SecretKey secretKey = kgen.generateKey();  
	                 byte[] enCodeFormat = secretKey.getEncoded(); */ 
	                 SecretKeySpec key = new SecretKeySpec(getKey(password), ALGORITHM_NAME);              
	                 Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);// 创建密码器   
	                cipher.init(Cipher.DECRYPT_MODE, key);// 初始化   
	                byte[] result = cipher.doFinal(content);  
	                return result; // 加密   
	        } catch (NoSuchAlgorithmException e) {  
	                e.printStackTrace();  
	        } catch (NoSuchPaddingException e) {  
	                e.printStackTrace();  
	        } catch (InvalidKeyException e) {  
	                e.printStackTrace();  
	        } catch (IllegalBlockSizeException e) {  
	                e.printStackTrace();  
	        } catch (BadPaddingException e) {  
	                e.printStackTrace();  
	        }  
	        return null;  
	}
	
	public static byte[] getKey(String key){
		byte[] b = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		byte[] keyBytes = key.getBytes();
		int len = keyBytes.length < 16 ? keyBytes.length : 16;
		for(int i=0; i<len; i++){
			b[i] = keyBytes[i];
		}
		return b;
	}

	public static byte[] encrypt(String paramString1, String paramString2, byte[] paramArrayOfByte) throws Exception {
		SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramString2.getBytes(), ALGORITHM_NAME);
		Cipher localCipher = Cipher.getInstance(TRANSFORMATION);
		IvParameterSpec localIvParameterSpec = new IvParameterSpec(paramString1.getBytes());
		localCipher.init(1, localSecretKeySpec, localIvParameterSpec);
		byte[] arrayOfByte = localCipher.doFinal(paramArrayOfByte);
		return arrayOfByte;
	}

	public static byte[] decrypt(String paramString1, String paramString2, byte[] paramArrayOfByte) throws Exception {
		SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramString2.getBytes(), ALGORITHM_NAME);
		Cipher localCipher = Cipher.getInstance(TRANSFORMATION);
		IvParameterSpec localIvParameterSpec = new IvParameterSpec(paramString1.getBytes());
		localCipher.init(2, localSecretKeySpec, localIvParameterSpec);
		byte[] arrayOfByte = localCipher.doFinal(paramArrayOfByte);
		return arrayOfByte;
	}

}
