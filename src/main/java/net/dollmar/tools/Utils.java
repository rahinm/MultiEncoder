package net.dollmar.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Hex;

public class Utils {

  public static boolean isEmptyString(final String s) {
    return s == null || s.isEmpty();
  }
  
  public static  byte[] getBytes(String data) {
    try {
      return data.getBytes("UTF-8");
    }
    catch (Exception e) {
      return null;
    }
  }  

  public static String calculateHash(final byte[] dataBytes, String algorithm) {
    try {
      MessageDigest md = MessageDigest.getInstance(algorithm);
      md.update(dataBytes);
      byte[] hashBytes = md.digest();
      return Hex.encodeHexString(hashBytes);
    }
    catch (NoSuchAlgorithmException e) {
      return e.getMessage();
    }
  }

  public static String prettyFormattedString(String binary, int blockSize, String separator) {

    List<String> result = new ArrayList<>();
    int index = 0;
    while (index < binary.length()) {
      result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
      index += blockSize;
    }

    return result.stream().collect(Collectors.joining(separator));
  }  

  public static String convertStringToBinaryEncodedString(String input) {
    String toBinary = input
        .chars()
        .boxed()
        .map(Integer::toBinaryString)
        .collect(Collectors.joining(" "));
    
    return toBinary;

  }  
  
  public static String convertBinaryEncodedStringToString(String input) {
    if (!isEmptyString(input)) {
      String[] encodedChars = input.split(" ");
      StringBuilder sb = new StringBuilder();
      for (String ec: encodedChars) {
        try {
          sb.append((char) Integer.parseInt(ec, 2));
        }
        catch (NumberFormatException e) {
          return "ERROR: Invalid Binary encoded string!!!";
        }
      }
      return sb.toString();
    }
    
    return null;
  }
  
  public static String convertStringToOctalEncodedString(String input) {
    String toOctal = input
        .chars()
        .boxed()
        .map(Integer::toOctalString)
        .collect(Collectors.joining(" "));
    
    return toOctal;
  }
  
  public static String convertOctalEncodedStringToString(String input) {
    if (!isEmptyString(input)) {
      String[] encodedChars = input.split(" ");
      StringBuilder sb = new StringBuilder();
      for (String ec: encodedChars) {
        try {
          sb.append((char) Integer.parseInt(ec, 8));
        }
        catch (NumberFormatException e) {
          return "ERROR: Invalid Octal string!!!";
        }
      }
      return sb.toString();
    }
    
    return null;
  }
  
  
  
}
