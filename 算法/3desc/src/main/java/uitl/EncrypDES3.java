package uitl;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * 3DES又称Triple DES，是DES加密算法的一种模式，它使用3条56位的密钥对3DES数据进行三次加密。
 */
public class EncrypDES3 {
    // KeyGenerator 提供对称密钥生成器的功能，支持各种算法
    private KeyGenerator keygen;
    // SecretKey 负责保存对称密钥
    private SecretKey deskey;
    // Cipher负责完成加密或解密工作
    private Cipher c;
    // 该字节数组负责保存加密的结果
    //private byte[] cipherByte;
    //保存密钥
    private byte[] keyByte;

    /**
     * 构造方法
     * @param desKeyString 密钥
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public EncrypDES3(String desKeyString) throws NoSuchAlgorithmException, NoSuchPaddingException {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        // 实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
        keygen = KeyGenerator.getInstance("DESede");
        if ("".equals(desKeyString)) {
            System.out.println("密钥是空的");
            // 生成密钥
            deskey = keygen.generateKey();
        } else {
            System.out.println("密钥不为空");
            deskey = new SecretKeySpec(desKeyString.getBytes(), "DESede");
        }
        //保存密钥
        keyByte = deskey.getEncoded();
        // 生成Cipher对象,指定其支持的DES算法
        c = Cipher.getInstance("DESede");
    }

    /**
     * 对字符串加密
     *
     * @param str
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public String Encrytor(String str) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, IOException {
        // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
        c.init(Cipher.ENCRYPT_MODE, deskey);
        // 加密，结果保存进cipherByte
        byte[] buf = c.doFinal(str.getBytes("utf-8"));
        String encontent=new sun.misc.BASE64Encoder().encode(buf);
        //保存密钥
        //saveFile("C:\\Users\\Zero\\Desktop\\key.dat", Base64Utils.encode(keyByte));
        return encontent;
    }

    /**
     * 对字符串解密
     *
     * @param secretData
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public String Decryptor(String secretData) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, IOException {
        // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
        c.init(Cipher.DECRYPT_MODE, deskey);
        byte[] buf = c.doFinal(Base64Utils.decode(secretData.toCharArray()));
        String decontent = new String(buf);
        return decontent;
    }

    /**
     * 加载文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String loadFile(String file) throws IOException {
        File f = new File(file);
        FileReader fr = new FileReader(f);
        BufferedReader bf = new BufferedReader(fr);
        StringBuilder msg = new StringBuilder();
        String str;
        while ((str = bf.readLine()) != null) {
            msg.append(str + "\n");
        }
        msg.deleteCharAt(msg.length() - 1);
        bf.close();
        return msg.toString();
    }

    /**
     * 保存文件
     *
     * @param file
     * @param message
     * @throws IOException
     */
    public static void saveFile(String file, String message) throws IOException {
        File f = new File(file);
        if (!f.exists()) {
            f.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(message.getBytes());
        fos.close();
    }

    /**
     * 加密文本文件
     *
     * @param sourceFile
     * @param desFile
     * @throws IOException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static void Encrytor(String sourceFile, String desFile) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        EncrypDES3 des = new EncrypDES3(null);
        String msg = loadFile(sourceFile);
        String encontent = des.Encrytor(msg);
        saveFile(desFile, encontent);
    }

    /**
     * 解密文本文件
     *
     * @param sourceFile
     * @param desFile
     * @param keyFile
     * @throws IOException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static void Decryptor(String sourceFile, String desFile, String keyFile) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        EncrypDES3 des = new EncrypDES3(null);
        String msg = loadFile(sourceFile);
        String key = loadFile(keyFile);
        String decontent = des.Decryptor(msg);
        saveFile(desFile, decontent);
    }

    /**
     * 测试方法
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String KEY = "05WOB22Z9jdrHxLVjGxAL47F";
        EncrypDES3 des = new EncrypDES3(KEY);
        String jsonString  = "{\"name\":\"telphone\",\"param\":\"18520278383\"}";
        System.out.println("【源参数】"+jsonString);
        String desString = des.Encrytor(jsonString);
        System.out.println("【des加密后】"+desString);
        String urlEncoderString = URLEncoder.encode(desString, "utf-8");
        System.out.println("【url加密后】"+urlEncoderString);
        System.out.println("【des解密后】"+des.Decryptor("AvR7ai1cixdbIiv1ngMwnYbmPhKKbDyi51k4GyL9y+3tqfzd4MeFNm/vwA/AoSIn"));


        //String msg = "{\"name\":\"telphone\",\"param\":\"18520278383\"}";
        //System.out.println("【加密前】：" + msg);
        //EncrypDES3 des = new EncrypDES3("05WOB22Z9jdrHxLVjGxAL47F");
        //System.out.println("【加密后】：" + des.Encrytor(msg));
        //Encrytor("C:\\Users\\Zero\\Desktop\\hint.txt", "C:\\Users\\Zero\\Desktop\\encontent.txt");
        //Decryptor("C:\\Users\\Zero\\Desktop\\encontent.txt", "C:\\Users\\Zero\\Desktop\\decontent.txt", "C:\\Users\\Zero\\Desktop\\key.dat");
    }

}
