package net.dollmar.tools;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.UIManager;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.Base64;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

/**
 * MultiEncoder is a simple GUI tool to convert text data into a variety of encoded
 * formats. Currently binary, octal, hex, base32 and base64 encoding formats are
 * supported. It is also possible to decode an encoded data into its raw text format
 * as well as other supported encoding formats.
 * 
 * This tool is inspired by the Translator, Binary on-line tool available at
 * https://paulschou.com/tools/xlate/ and written in Java for local execution on
 * different platforms. 
 * 
 * This is a free software. Permission is granted for all forms of use 
 * except for any form intended for causing malicious damage.
 *  
 * @author Mohammad A. Rahin / Dollmar Enterprises Ltd
 */
public class MultiEncoder {

  private static final String VERSION_NUMBER = "0.2-SNAPSHOT";
  private static final String COPYRIGHT = "(c) 2021 Dollmar Enterprises Ltd.";

  private JFrame frame;
  private JTextArea textArea;
  private JTextArea hashValues;
  private JTextField binaryData;
  private JTextField octalData;
  private JTextField hexData;
  private JTextField base32Data;
  private JTextField base64Data;


  /**
   * Create the application.
   */
  public MultiEncoder() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame("MultiEncoder");
    frame.setResizable(false);
    frame.setBounds(100, 100, 600, 720);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container contentPane = frame.getContentPane();

    JPanel hdrPanel = new JPanel();
    hdrPanel.setBackground(UIManager.getColor("Button.focus"));
    hdrPanel.setLayout(new GridLayout(2, 1, 0, 0));
    hdrPanel.setBorder(BorderFactory.createTitledBorder(""));
    contentPane.add(hdrPanel, BorderLayout.NORTH);

    JLabel header = new JLabel("<html><h4><FONT COLOR=BLUE>MultiEncoder: Multi Encoding/Decoding Tool</FONT></h4></html>", JLabel.CENTER);
    JLabel vInfo = new JLabel("Version: " + VERSION_NUMBER + " " + COPYRIGHT, JLabel.CENTER);
    hdrPanel.add(header);
    hdrPanel.add(vInfo);

    JPanel mainPanel = new JPanel();
    mainPanel.setBorder(BorderFactory.createTitledBorder(""));
    contentPane.add(mainPanel); 
    mainPanel.setLayout(null);

    JPanel textPanel = new JPanel();
    textPanel.setBorder(BorderFactory.createTitledBorder("Text"));
    textPanel.setBounds(12, 12, 281, 208);
    mainPanel.add(textPanel);
    textPanel.setLayout(null);

    JButton btnEncode = new JButton("Encode");
    btnEncode.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        byte[] dataBytes = Utils.getBytes(textArea.getText().trim());
        setHashValues(dataBytes);
        setBinaryEncodedData(dataBytes);
        setOctalEncodedData(dataBytes);
        setHexEncodedData(dataBytes);
        setBase32EncodedData(dataBytes);
        setBase64EncodedData(dataBytes);
      }
    });
    btnEncode.setBounds(12, 173, 87, 19);
    textPanel.add(btnEncode);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(12, 28, 258, 133);
    textPanel.add(scrollPane);

    textArea = new JTextArea();
    scrollPane.setViewportView(textArea);
    textArea.setToolTipText("Enter printable text you wish to encode here");
    textArea.setEditable(true);

    JPanel hashPanel = new JPanel();
    hashPanel.setBorder(BorderFactory.createTitledBorder("Hash Values"));
    hashPanel.setBounds(310, 12, 281, 208);
    mainPanel.add(hashPanel);
    hashPanel.setLayout(null);

    JScrollPane scrollPane_1 = new JScrollPane();
    scrollPane_1.setBounds(12, 28, 258, 133);
    hashPanel.add(scrollPane_1);
    
    hashValues = new JTextArea();
    scrollPane_1.setViewportView(hashValues);

    JButton btnReset = new JButton("Reset");
    btnReset.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        hashValues.setText("");
      }
    });
    btnReset.setBounds(22, 173, 87, 19);
    hashPanel.add(btnReset);
    
    JPanel encPanel = new JPanel();
    encPanel.setBounds(12, 232, 576, 375);
    encPanel.setBorder(BorderFactory.createTitledBorder("Encoded Data"));
    mainPanel.add(encPanel);
    encPanel.setLayout(null);

    JLabel lblBinary = new JLabel("Binary");
    lblBinary.setBounds(12, 23, 70, 15);
    encPanel.add(lblBinary);

    binaryData = new JTextField();
    binaryData.setColumns(10);
    binaryData.setBounds(12, 38, 552, 19);
    encPanel.add(binaryData);

    JButton btnBinaryDecode = new JButton("Decode");
    btnBinaryDecode.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String fieldVal = binaryData.getText().trim();
        if (!Utils.isEmptyString(fieldVal)) {
          String textData = Utils.convertBinaryEncodedStringToString(fieldVal);
          byte[] dataBytes = Utils.getBytes(textData);

          setHashValues(dataBytes);
          setTextData(textArea, dataBytes);
          setOctalEncodedData(dataBytes);
          setHexEncodedData(dataBytes);
          setBase32EncodedData(dataBytes);
          setBase64EncodedData(dataBytes);          
        }
      }
    });
    btnBinaryDecode.setBounds(12, 62, 87, 19);
    encPanel.add(btnBinaryDecode);

    JLabel lblOctal = new JLabel("Octal");
    lblOctal.setBounds(12, 93, 70, 15);
    encPanel.add(lblOctal);

    octalData = new JTextField();
    octalData.setColumns(10);
    octalData.setBounds(12, 108, 552, 19);
    encPanel.add(octalData);

    JButton btnOctalDecode = new JButton("Decode");
    btnOctalDecode.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String fieldVal = octalData.getText().trim();
        if (!Utils.isEmptyString(fieldVal)) {
          String textData = Utils.convertOctalEncodedStringToString(fieldVal);
          byte[] dataBytes = Utils.getBytes(textData);

          setHashValues(dataBytes);
          setTextData(textArea, dataBytes);
          setBinaryEncodedData(dataBytes);
          setHexEncodedData(dataBytes);
          setBase32EncodedData(dataBytes);
          setBase64EncodedData(dataBytes);          
        }
      }
    });
    btnOctalDecode.setBounds(12, 132, 87, 19);
    encPanel.add(btnOctalDecode);

    JLabel lblHex = new JLabel("Hex");
    lblHex.setBounds(12, 163, 70, 15);
    encPanel.add(lblHex);

    hexData = new JTextField();
    hexData.setColumns(10);
    hexData.setBounds(12, 178, 552, 19);
    encPanel.add(hexData);

    JButton btnHexDecode = new JButton("Decode");
    btnHexDecode.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String fieldVal = hexData.getText().trim();
        if (!Utils.isEmptyString(fieldVal)) {
          byte[] dataBytes = null;
          try {
            dataBytes = Hex.decodeHex(fieldVal.replaceAll(" ", ""));
          }
          catch (DecoderException de) {
            dataBytes = Utils.getBytes("ERROR: Invalid hex string!!!");
          }
          setHashValues(dataBytes);
          setTextData(textArea, dataBytes);
          setBinaryEncodedData(dataBytes);
          setOctalEncodedData(dataBytes);
          setBase32EncodedData(dataBytes);
          setBase64EncodedData(dataBytes);
        }
      }
    });
    btnHexDecode.setBounds(12, 202, 87, 19);
    encPanel.add(btnHexDecode);

    JLabel lblBase = new JLabel("Base32");
    lblBase.setBounds(12, 233, 70, 15);
    encPanel.add(lblBase);

    base32Data = new JTextField();
    base32Data.setColumns(10);
    base32Data.setBounds(12, 248, 552, 19);
    encPanel.add(base32Data);

    JButton btnBase32Decode = new JButton("Decode");
    btnBase32Decode.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String fieldVal = base32Data.getText().trim();
        if (!Utils.isEmptyString(fieldVal)) {
          byte[] dataBytes = new Base32().decode(fieldVal);
          setHashValues(dataBytes);
          setTextData(textArea, dataBytes);
          setBinaryEncodedData(dataBytes);
          setOctalEncodedData(dataBytes);
          setHexEncodedData(dataBytes);
          setBase64EncodedData(dataBytes);
        }
      }
    });
    btnBase32Decode.setBounds(12, 272, 87, 19);
    encPanel.add(btnBase32Decode);

    JLabel lblBase_1 = new JLabel("Base64");
    lblBase_1.setBounds(12, 303, 70, 15);
    encPanel.add(lblBase_1);

    base64Data = new JTextField();
    base64Data.setColumns(10);
    base64Data.setBounds(12, 318, 552, 19);
    encPanel.add(base64Data);

    JButton btnBase64Decode = new JButton("Decode");
    btnBase64Decode.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String fieldVal = base64Data.getText().trim();
        if (!Utils.isEmptyString(fieldVal)) {
          byte[] dataBytes = Base64.getDecoder().decode(fieldVal);
          setHashValues(dataBytes);
          setTextData(textArea, dataBytes);
          setBinaryEncodedData(dataBytes);
          setOctalEncodedData(dataBytes);
          setHexEncodedData(dataBytes);
          setBase32EncodedData(dataBytes);
        }
      }
    });
    btnBase64Decode.setBounds(12, 342, 87, 19);
    encPanel.add(btnBase64Decode);
  }


  private void setTextFieldValue(JTextField textField, final String val) {
    textField.setText(val);
    textField.requestFocus();
    textField.setCaretPosition(0);    
  }


  private void setHashValues(final byte[] dataBytes) {
    StringBuilder sb = new StringBuilder();
    sb.append("MD5: ").append(Utils.calculateHash(dataBytes, "MD5")).append(System.getProperty("line.separator"));
    sb.append("SHA1: ").append(Utils.calculateHash(dataBytes, "SHA1")).append(System.getProperty("line.separator"));;
    sb.append("SHA256: ").append(Utils.calculateHash(dataBytes, "SHA-256")).append(System.getProperty("line.separator"));;
    sb.append("SHA384: ").append(Utils.calculateHash(dataBytes, "SHA-384")).append(System.getProperty("line.separator"));;
    sb.append("SHA512: ").append(Utils.calculateHash(dataBytes, "SHA-512"));
    
    setTextAreaValue(hashValues, sb.toString());
  }

  private void setTextAreaValue(JTextArea textArea, final String data) {
    textArea.setText(data);
    textArea.requestFocus();
    textArea.setCaretPosition(0);    
  }


  private void setTextData(JTextArea textArea, final byte[] dataBytes) {
    setTextAreaValue(textArea, new String(dataBytes));
  }


  private void setBinaryEncodedData(final byte[] dataBytes) {
    setTextFieldValue(binaryData, Utils.convertStringToBinaryEncodedString(new String(dataBytes)));
  }

  private void setOctalEncodedData(final byte[] dataBytes) {
    setTextFieldValue(octalData, Utils.convertStringToOctalEncodedString(new String(dataBytes)));
  }


  private void setHexEncodedData(final byte[] dataBytes) {
    setTextFieldValue(hexData, Utils.prettyFormattedString(Hex.encodeHexString(dataBytes), 2, " "));
  }


  private void setBase32EncodedData(final byte[] dataBytes) {
    setTextFieldValue(base32Data, new String(new Base32().encode(dataBytes)));
  }

  private void setBase64EncodedData(final byte[] dataBytes) {
    setTextFieldValue(base64Data, Base64.getEncoder().encodeToString(dataBytes));
  }

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          MultiEncoder window = new MultiEncoder();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
}
