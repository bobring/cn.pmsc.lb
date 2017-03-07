package aliyun.oss;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSErrorCode;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;

/**
 *
 * @ClassName: TestOSS
 *
 * @Description: 该示例代码展示了如果在OSS中创建和删除一个Bucket，以及如何上传和下载一个文件。
 *
 *               该示例代码的执行过程是： 1. 创建一个Bucket（如果已经存在，则忽略错误码）； 2. 上传一个文件到OSS； 3.
 *               下载这个文件到本地； 4. 清理测试资源：删除Bucket及其中的所有Objects。
 *
 *               尝试运行这段示例代码时需要注意： 1. 为了展示在删除Bucket时除了需要删除其中的Objects,
 *               示例代码最后为删除掉指定的Bucket，因为不要使用您的已经有资源的Bucket进行测试！ 2.
 *               请使用您的API授权密钥填充ACCESS_ID和ACCESS_KEY常量； 3.
 *               需要准确上传用的测试文件，并修改常量uploadFilePath为测试文件的路径；
 *               修改常量downloadFilePath为下载文件的路径。 4. 该程序仅为示例代码，仅供参考，并不能保证足够健壮。 OSS
 *               Java
 *               API手册：http://aliyun_portal_storage.oss.aliyuncs.com/oss_api/oss_javahtml/index.html?spm=5176.7150518.1996836753.8.d5TjaG
 *               OSS Java SDK开发包:http://help.aliyun.com/view/13438814.html
 *               OSSClient:www.mvnrepository.com/artifact/cglib/cglib/2.2
 * @author PineTree
 * @date 2014年12月1日 下午3:23:32
 * @version
 * 
 * http://help.aliyun.com/view/11108271_13438690.html?spm=5176.7114037.1996646101.10.c7Lr2Q&pos=5 
https://help.aliyun.com/document_detail/31837.html?spm=5176.7739584.2.1.Z6lq3V
青岛节点外网地址： oss-cn-qingdao.aliyuncs.com
青岛节点内网地址： oss-cn-qingdao-internal.aliyuncs.com
北京节点外网地址：oss-cn-beijing.aliyuncs.com
北京节点内网地址：oss-cn-beijing-internal.aliyuncs.com
杭州节点外网地址： oss-cn-hangzhou.aliyuncs.com
杭州节点内网地址： oss-cn-hangzhou-internal.aliyuncs.com
香港节点外网地址： oss-cn-hongkong.aliyuncs.com
香港节点内网地址： oss-cn-hongkong-internal.aliyuncs.com
深圳节点外网地址： oss-cn-shenzhen.aliyuncs.com
深圳节点内网地址： oss-cn-shenzhen-internal.aliyuncs.com
原地址oss.aliyuncs.com 默认指向杭州节点外网地址
原内网地址oss-internal.aliyuncs.com 默认指向杭州节点内网地址
 */
public class TestOSS {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TestOSS.class);

	/**
	 * 阿里云ACCESS_ID,accessKey请登录https://ak-console.aliyun.com/#/查看
	 */
	private static String ACCESS_ID = "你的ACCESS_ID";
	/**
	 * 阿里云ACCESS_KEY,accessKey请登录https://ak-console.aliyun.com/#/查看
	 */
	private static String ACCESS_KEY = "你的ACCESS_KEY";
	/**
	 * 阿里云OSS_ENDPOINT 青岛Url
	 */
	private static String OSS_ENDPOINT = "http://oss-cn-qingdao.aliyuncs.com";

	/**
	 * 阿里云BUCKET_NAME OSS
	 */
	private static String BUCKET_NAME = "demo10";

	public static String getACCESS_ID() {
		return ACCESS_ID;
	}

	public static void setACCESS_ID(String aCCESS_ID) {
		ACCESS_ID = aCCESS_ID;
	}

	public static String getACCESS_KEY() {
		return ACCESS_KEY;
	}

	public static void setACCESS_KEY(String aCCESS_KEY) {
		ACCESS_KEY = aCCESS_KEY;
	}

	public static String getOSS_ENDPOINT() {
		return OSS_ENDPOINT;
	}

	public static void setOSS_ENDPOINT(String oSS_ENDPOINT) {
		OSS_ENDPOINT = oSS_ENDPOINT;
	}

	public static String getBUCKET_NAME() {
		return BUCKET_NAME;
	}

	public static void setBUCKET_NAME(String bUCKET_NAME) {
		BUCKET_NAME = bUCKET_NAME;
	}
	
	/**
	 * @param Map<String, String> filelist
	 * @param Map<Local file, Remote file> filelist
	 */
	public static void download(Map<String, String> filelist) {
		// String bucketName = "demo10";
//		String Objectkey = "photo1.jpg";
//
//		String uploadFilePath = "D:\\photo.jpg";
//		String downloadFilePath = "D:\\photo1.jpg";

		// 使用默认的OSS服务器地址创建OSSClient对象,不叫OSS_ENDPOINT代表使用杭州节点，青岛节点要加上不然包异常
		OSSClient client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY);

		// 如果你想配置OSSClient的一些细节的参数，可以在构造OSSClient的时候传入ClientConfiguration对象。
		// ClientConfiguration是OSS服务的配置类，可以为客户端配置代理，最大连接数等参数。
		// 具体配置看http://aliyun_portal_storage.oss.aliyuncs.com/oss_api/oss_javahtml/OSSClient.html#id2
		// ClientConfiguration conf = new ClientConfiguration();
		// OSSClient client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY,
		// conf);

		try {
			ensureBucket(client, BUCKET_NAME);
			setBucketPublicReadable(client, BUCKET_NAME);

//			System.out.println("正在上传...");
//			uploadFile(client, BUCKET_NAME, Objectkey, uploadFilePath);
			
			for(Map.Entry<String, String> a: filelist.entrySet()) {
				if (logger.isInfoEnabled()) {
					logger.info("正在下载... " + a.getValue() + " --> " + a.getKey()); //$NON-NLS-1$
				}

				downloadFile(client, BUCKET_NAME, a.getValue(), a.getKey());
			}

//			System.out.println("正在下载...");
//			downloadFile(client, BUCKET_NAME, Objectkey, downloadFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			deleteBucket(client, BUCKET_NAME);
		}
	}

	/**
	 * @param Map<String, String> filelist
	 * @param Map<Local file, Remote file> filelist
	 */
	public static int upload(Map<String, String> filelist) {
		// String bucketName = "demo10";
//		String Objectkey = "photo1.jpg";
//
//		String uploadFilePath = "D:\\photo.jpg";
//		String downloadFilePath = "D:\\photo1.jpg";

		// 使用默认的OSS服务器地址创建OSSClient对象,不叫OSS_ENDPOINT代表使用杭州节点，青岛节点要加上不然包异常
		OSSClient client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY);

		// 如果你想配置OSSClient的一些细节的参数，可以在构造OSSClient的时候传入ClientConfiguration对象。
		// ClientConfiguration是OSS服务的配置类，可以为客户端配置代理，最大连接数等参数。
		// 具体配置看http://aliyun_portal_storage.oss.aliyuncs.com/oss_api/oss_javahtml/OSSClient.html#id2
		// ClientConfiguration conf = new ClientConfiguration();
		// OSSClient client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY,
		// conf);
		int count = 0;
		
		try {
			ensureBucket(client, BUCKET_NAME);
			setBucketPublicReadable(client, BUCKET_NAME);

//			System.out.println("正在上传...");
//			uploadFile(client, BUCKET_NAME, Objectkey, uploadFilePath);
			
			for(Map.Entry<String, String> a: filelist.entrySet()) {
				try {
					if (logger.isInfoEnabled()) {
						logger.info("uploading... " + a.getKey() + " --> " + a.getValue()); //$NON-NLS-1$
					}
					uploadFile(client, BUCKET_NAME, a.getValue(), a.getKey());
					count++;
				} catch (Exception e) {
					logger.error("upload(Map<String,String>) - e=" + e, e); //$NON-NLS-1$
				}
			}
			
//			System.out.println("正在下载...");
//			downloadFile(client, BUCKET_NAME, Objectkey, downloadFilePath);
		} catch (Exception e) {
			logger.error("upload(Map<String,String>) - e=" + e, e); //$NON-NLS-1$
//			e.printStackTrace();
		} finally {
//			deleteBucket(client, BUCKET_NAME);
		}
		
		return count;
	}

	/**
	 * 创建Bucket
	 *
	 * @param client
	 *            OSSClient对象
	 * @param bucketName
	 *            BUCKET名
	 * @throws OSSException
	 * @throws ClientException
	 */
	public static void ensureBucket(OSSClient client, String bucketName) throws OSSException, ClientException {
		try {
			client.createBucket(bucketName);
		} catch (ServiceException e) {
			if (!OSSErrorCode.BUCKET_ALREADY_EXISTS.equals(e.getErrorCode())) {
				throw e;
			}
		}
	}

	/**
	 * 删除一个Bucket和其中的Objects
	 *
	 * @param client
	 *            OSSClient对象
	 * @param bucketName
	 *            Bucket名
	 * @throws OSSException
	 * @throws ClientException
	 */
	private static void deleteBucket(OSSClient client, String bucketName) throws OSSException, ClientException {
		ObjectListing ObjectListing = client.listObjects(bucketName);
		List<OSSObjectSummary> listDeletes = ObjectListing.getObjectSummaries();
		for (int i = 0; i < listDeletes.size(); i++) {
			String objectName = listDeletes.get(i).getKey();
			System.out.println("objectName = " + objectName);
			// 如果不为空，先删除bucket下的文件
			client.deleteObject(bucketName, objectName);
		}
		client.deleteBucket(bucketName);
	}

	/**
	 * 把Bucket设置成所有人可读
	 *
	 * @param client
	 *            OSSClient对象
	 * @param bucketName
	 *            Bucket名
	 * @throws OSSException
	 * @throws ClientException
	 */
	private static void setBucketPublicReadable(OSSClient client, String bucketName)
			throws OSSException, ClientException {
		// 创建bucket
		client.createBucket(bucketName);

		// 设置bucket的访问权限， public-read-write权限
		client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
	}

	/**
	 * 上传文件
	 *
	 * @param client
	 *            OSSClient对象
	 * @param bucketName
	 *            Bucket名
	 * @param Objectkey
	 *            上传到OSS起的名
	 * @param filename
	 *            本地文件名
	 * @throws OSSException
	 * @throws ClientException
	 * @throws IOException 
	 */
	private static void uploadFile(OSSClient client, String bucketName, String Objectkey, String filename)
			throws OSSException, ClientException, IOException {
		File file = new File(filename);
		ObjectMetadata objectMeta = new ObjectMetadata();
		objectMeta.setContentLength(file.length());
		// 判断上传类型，多的可根据自己需求来判定
		if (filename.toLowerCase(Locale.ENGLISH).endsWith("xml")) {
			objectMeta.setContentType("text/xml");
		} else if (filename.toLowerCase(Locale.ENGLISH).endsWith("jpg")) {
			objectMeta.setContentType("image/jpeg");
		} else if (filename.toLowerCase(Locale.ENGLISH).endsWith("png")) {
			objectMeta.setContentType("image/png");
		}

		InputStream input = new FileInputStream(file);
		client.putObject(bucketName, Objectkey, input, objectMeta);
		input.close();
		
		if (logger.isInfoEnabled()) {
			logger.info("uploaded: " + filename + " , size : " + file.length()); //$NON-NLS-1$
		}
	}

	/**
	 * 下载文件
	 *
	 * @param client
	 *            OSSClient对象
	 * @param bucketName
	 *            Bucket名
	 * @param Objectkey
	 *            上传到OSS起的名
	 * @param filename
	 *            文件下载到本地保存的路径
	 * @throws OSSException
	 * @throws ClientException
	 */
	private static void downloadFile(OSSClient client, String bucketName, String Objectkey, String filename)
			throws OSSException, ClientException {
		client.getObject(new GetObjectRequest(bucketName, Objectkey), new File(filename));
	}
}
