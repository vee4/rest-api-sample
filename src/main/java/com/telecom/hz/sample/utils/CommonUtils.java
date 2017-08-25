package com.telecom.hz.sample.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.telecom.hz.sample.exception.IllegalParameterException;

public class CommonUtils {
	
	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);
	
	private CommonUtils() {
	}

	/**
	 * sign the data in sha-256.
	 * 
	 * @param data
	 *            data to be signed.
	 * @return the digest result in hex char or char array with length 0 if the SHA-256 algorithm is not supported.
	 */
	public static char[] sha256digest(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			return Hex.encode(md.digest(data));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new char[0];
	}

	/**
	 * read the data from specify path,the file cannot greater than 2 gigabytes, if
	 * so, the exception will be catch and return a byte array with length 0.
	 * 
	 * @param path
	 *            the path of the file.
	 * @return the byte data of the file or byte array with length 0.
	 */
	public static byte[] read(String path) {
		FileInputStream fis = null;
		ByteArrayOutputStream baos = null;
		try {
			File file = new File(path);
			fis = new FileInputStream(file);
//			FileChannel channel = fis.getChannel();
			if (file.length() > Integer.MAX_VALUE) {
				throw new IllegalArgumentException("file's size can not greater than " + Integer.MAX_VALUE + " bytes.");
			}
			baos = new ByteArrayOutputStream((int) file.length());
//			ByteBuffer buffer = ByteBuffer.allocate(64);
			int length = -1;
			byte[] buffer = new byte[64];
			while ((length=fis.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
//				buffer.clear();
			}
			return baos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (baos != null) {
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return new byte[0];
	}
	
	/**
	 *<p>validate the obj's parameter according to the value of the parameter is null or not.
	 * if the validate pass, then nothing will happen, else the IllegalParameterException will
	 * be throwed.</p>
	  *<p>the additional point that should be pay attention is:</br>
	 * the parameter define in the obj must not use the base data type of java, such int long etc.
	 * use Integer, Long etc instead.</p>
	 * @param obj
	 * @throws IllegalParameterException
	 */
	public static <T> void validate(T obj) throws IllegalParameterException{
	
		try {
			Class<? extends Object> clazz = obj.getClass();
			
			Set<ConstraintViolation<T>> violations = validator.validate(obj);
			if(violations.size()>0) {
				StringBuilder builder = new StringBuilder();
				for (ConstraintViolation<T> violation : violations) {
					String name = violation.getPropertyPath().toString();
					System.out.println("reflect on field :"+ name);
					Field field = clazz.getDeclaredField(name);
					field.setAccessible(true);
					Object value = field.get(obj);
					if(value != null) {
						builder.append(violation.getMessage()+"\n");
					}
				}
				System.out.println("content in builder:"+builder.toString());
				if(builder.toString().length()>0) {
					throw new IllegalParameterException(builder.toString());
				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * handles the validate result.
	 * @param result the result of validation.
	 * @throws IllegalParameterException if the validate result contains errors.
	 */
	public static void handleValidateResult(BindingResult result) throws IllegalParameterException {
		if (result.hasErrors()) {
			// if validate failure.
			StringBuilder builder = new StringBuilder();
			List<FieldError> fieldErrors = result.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				builder.append(
						String.format("argument '%s': %s \n", fieldError.getField(), fieldError.getDefaultMessage()));
			}
			String msg = builder.toString();
			logger.warn(msg);
			throw new IllegalParameterException(msg);
		}
	}
}














