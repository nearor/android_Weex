package com.nearor.mylibrary.secure;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSA {
	public static final String ALGORITHM_RSA = "RSA";
	// 'transformation' should be of the form "algorithm/mode/padding".
	public static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	public static final String PUBLIC_KEY = "PublicKey";
	public static final String PRIVATE_KEY = "PrivateKey";
	public static final int MAX_DECRYPT_BLOCK = 1024 / 8;
	public static final int MAX_ENCRYPT_BLOCK = 1024 / 8 - 11;

	public static Map<String, Object> generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator localKeyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
		localKeyPairGenerator.initialize(1024);
		KeyPair localKeyPair = localKeyPairGenerator.generateKeyPair();
		RSAPublicKey localRSAPublicKey = (RSAPublicKey) localKeyPair.getPublic();
		RSAPrivateKey localRSAPrivateKey = (RSAPrivateKey) localKeyPair.getPrivate();
		HashMap<String, Object> localHashMap = new HashMap<String, Object>(2);
		localHashMap.put(PUBLIC_KEY, localRSAPublicKey);
		localHashMap.put(PRIVATE_KEY, localRSAPrivateKey);
		return localHashMap;
	}

	public static String getPublicKey(Map<String, Object> paramMap) throws UnsupportedEncodingException {
		Key localKey = (Key) paramMap.get(PUBLIC_KEY);
		return Base64Encoder.encodeToString(localKey.getEncoded());
	}

	public static String getPrivateKey(Map<String, Object> paramMap) throws Exception {
		Key localKey = (Key) paramMap.get(PRIVATE_KEY);
		return Base64Encoder.encodeToString(localKey.getEncoded());
	}

	/** */
	/**
	 * <p>
	 * 用私钥对信息生成数字签名
	 * </p>
	 * 
	 * @param data
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64Encoder.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(privateK);
		signature.update(data);
		return Base64Encoder.encodeToString(signature.sign());
	}

	/** */
	/**
	 * <p>
	 * 校验数字签名
	 * </p>
	 * 
	 * @param data
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @param sign
	 *            数字签名
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		byte[] keyBytes = Base64Encoder.decode(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(Base64Encoder.decode(sign));
	}

	public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
		byte[] arrayOfByte = Base64Encoder.decode(privateKey);
		PKCS8EncodedKeySpec localPKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(arrayOfByte);
		KeyFactory localKeyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
		PrivateKey localPrivateKey = localKeyFactory.generatePrivate(localPKCS8EncodedKeySpec);
		Cipher localCipher = Cipher.getInstance(TRANSFORMATION);
		localCipher.init(Cipher.DECRYPT_MODE, localPrivateKey);

		return crypt(encryptedData, localCipher, MAX_DECRYPT_BLOCK);
	}

	public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
		byte[] arrayOfByte = Base64Encoder.decode(publicKey);
		X509EncodedKeySpec localX509EncodedKeySpec = new X509EncodedKeySpec(arrayOfByte);
		KeyFactory localKeyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
		PublicKey localPublicKey = localKeyFactory.generatePublic(localX509EncodedKeySpec);
		Cipher localCipher = Cipher.getInstance(TRANSFORMATION);
		localCipher.init(Cipher.DECRYPT_MODE, localPublicKey);

		return crypt(encryptedData, localCipher, MAX_DECRYPT_BLOCK);
	}

	public static byte[] encryptByPrivateKey(byte[] dataTobeEncrypt, String privateKey) throws Exception {
		byte[] privateKeyBytes = Base64Encoder.decode(privateKey);
		KeyFactory localKeyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
		PrivateKey localPrivateKey = localKeyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
		Cipher localCipher = Cipher.getInstance(TRANSFORMATION);
		localCipher.init(Cipher.ENCRYPT_MODE, localPrivateKey);

		return crypt(dataTobeEncrypt, localCipher, MAX_ENCRYPT_BLOCK);
	}

	public static byte[] encryptByPublicKey(byte[] dataTobeEncrypt, String publicKey) throws Exception {
		byte[] publicKeyBytes = Base64Encoder.decode(publicKey);
		KeyFactory localKeyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
		PublicKey localPublicKey = localKeyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
		Cipher localCipher = Cipher.getInstance(TRANSFORMATION);
		localCipher.init(Cipher.ENCRYPT_MODE, localPublicKey);

		return crypt(dataTobeEncrypt, localCipher, MAX_ENCRYPT_BLOCK);
	}

	private static byte[] crypt(byte[] data, Cipher localCipher, int maxBlock) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int length = data.length;
		int cryptLength = 0;
		byte[] cache;
		while (cryptLength < length) {
			int unCryptLength = length - cryptLength;
			int block = Math.min(maxBlock, unCryptLength);
			cache = localCipher.doFinal(data, cryptLength, block);
			out.write(cache, 0, cache.length);
			cryptLength += block;
		}
		byte[] cryptedData = out.toByteArray();
		out.close();
		return cryptedData;
	}

	public static RSAPublicKey generateRSAPublicKey(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
			throws Exception {
		KeyFactory localKeyFactory = null;
		try {
			localKeyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
			throw new Exception(localNoSuchAlgorithmException.getMessage());
		}
		RSAPublicKeySpec localRSAPublicKeySpec = new RSAPublicKeySpec(paramBigInteger1, paramBigInteger2);
		try {
			return (RSAPublicKey) localKeyFactory.generatePublic(localRSAPublicKeySpec);
		} catch (InvalidKeySpecException localInvalidKeySpecException) {
			throw new Exception(localInvalidKeySpecException.getMessage());
		}
	}

	public static RSAPrivateKey generateRSAPrivateKey(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
			throws Exception {
		KeyFactory localKeyFactory = null;
		try {
			localKeyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
			throw new Exception(localNoSuchAlgorithmException.getMessage());
		}
		RSAPrivateKeySpec localRSAPrivateKeySpec = new RSAPrivateKeySpec(paramBigInteger1, paramBigInteger2);
		try {
			return (RSAPrivateKey) localKeyFactory.generatePrivate(localRSAPrivateKeySpec);
		} catch (InvalidKeySpecException localInvalidKeySpecException) {
			throw new Exception(localInvalidKeySpecException.getMessage());
		}
	}

}