package JsoupDemo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.DBUtil;

public class CityDemo {
	private static String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015";
	public static void main(String[] args) throws SQLException {
//		readProvince("/index.html");
//		readCity("/11.html");
		readQu("/11/1101.html");
//		readCounty("/11/01/110101.html");
//		readVillagetr("/11/01/01/110101001.html");
	}
	//省
	public static void readProvince(String strUrl){
		String proUrl = url+strUrl;
		try {
			Document doc = Jsoup.connect(proUrl).get();
			Elements links = doc.select("td>a");
			System.out.println(links.size());
			FileOutputStream fos = new FileOutputStream("F:\\"+links.get(0).text()+".txt", false);  
            OutputStreamWriter osw = new OutputStreamWriter(fos, "gb2312");  
//			for(Element e : links){
//				String linkText = e.text();
//				System.out.println(linkText);
//				osw.write(linkText+"\r\n");
//			}
            osw.write(links.get(0).text());
            saveData(0, 0, links.get(0).text(),"0", "");
            osw.close();  
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//市
		public static void readCity(String strUrl){
			String proUrl = url+strUrl;
			try {
				Document doc = Jsoup.connect(proUrl).get();
				Elements links = doc.getElementsByClass("citytr");
				System.out.println(links.size());
				FileOutputStream fos = new FileOutputStream("F:\\北京直辖市.txt", false);  
	            OutputStreamWriter osw = new OutputStreamWriter(fos, "gb2312");  
				for(Element e : links){
					String[] linkText = e.text().split(" ");
					System.out.println(Arrays.toString(linkText));
					osw.write(linkText[0]+","+linkText[1]+"\r\n");
				}
				saveData(2, 1, "北京市","0,1", "");
				osw.close(); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	//区
	public static void readQu(String strUrl){
		String proUrl = url+strUrl;
		try {
			Document doc = Jsoup.connect(proUrl).get();
			Elements links = doc.getElementsByClass("countytr");
			System.out.println(links.size());
			FileOutputStream fos = new FileOutputStream("F:\\北京区.txt", false);  
            OutputStreamWriter osw = new OutputStreamWriter(fos, "gb2312");  
			for(Element e : links){
				String[] linkText = e.text().split(" ");
				System.out.println(Arrays.toString(linkText));
				osw.write(linkText[0]+","+linkText[1]+"\r\n");
	            saveData(3, 2, linkText[1],"0,1,2", linkText[0]);
			}
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//镇
	public static void readCounty(String strUrl){
		String proUrl = url+strUrl;
		try {
			Document doc = Jsoup.connect(proUrl).get();
			Elements links = doc.getElementsByClass("towntr");
			System.out.println(links.size());
			FileOutputStream fos = new FileOutputStream("F:\\北京镇.txt", false);  
            OutputStreamWriter osw = new OutputStreamWriter(fos, "gb2312"); 
			for(Element e : links){
				String linkText = e.text();
				System.out.println(linkText);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//社区
	public static void readVillagetr(String strUrl){
		String proUrl = url+strUrl;
		try {
			Document doc = Jsoup.connect(proUrl).get();
			Elements links = doc.getElementsByClass("villagetr");
			System.out.println(links.size());
			for(Element e : links){
				String linkText = e.text();
				System.out.println(linkText);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//存数据
	public static void saveData(int grade,int parent,String name,String content,String code){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = 
				"insert into tbl_staff_region_new(order_tag,invalid,created_datetime,grade,parent,name,content,code) values(" +
				"0,0,?,"+grade+","+parent+",'"+name+"','"+content+"','"+code+"')";
		System.out.println(sql);
		try {
			conn = DBUtil.getConnect();
			ps = conn.prepareStatement(sql);
			ps.setDate(1, new Date(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, ps);
		}
	}
}
