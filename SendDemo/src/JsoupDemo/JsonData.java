package JsoupDemo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;

import util.DBUtil;

import entity.StaffRegion;

public class JsonData {
	public static void main(String[] args) throws Exception {
		Map<Object,Object> map = new LinkedHashMap<Object,Object>();
		Map<Object,Object> proMap = new LinkedHashMap<Object, Object>();
		
		List<StaffRegion> proList = getListData(1);
//		int count = 1;
		for(StaffRegion region : proList){
			proMap.put(region.getId(),region.getName());
			map.put(region.getContent(), proMap);
			List<StaffRegion> cityList = getListByParent(region.getId());
			Map<Object,Object> cityMap = new LinkedHashMap<Object, Object>();
//			System.out.println(count++);
			for(StaffRegion creg : cityList){
				cityMap.put(creg.getId(), creg.getName());
				map.put(creg.getContent(), cityMap);
				List<StaffRegion> counList = getListByParent(creg.getId());
				Map<Object,Object> counMap = new LinkedHashMap<Object, Object>();
//				System.out.println(count++);
				for(StaffRegion coureg : counList){
					counMap.put(coureg.getId(), coureg.getName());
					map.put(coureg.getContent(),counMap);
//					System.out.println(count++);
				}
			}
		}  

		String json = JSON.toJSONString(map);
		String path = "F://location.js";
		InputStream in = new ByteArrayInputStream(json.getBytes());
		FileUtils.copyInputStreamToFile(in, new File(path));
	}
	
	public static List<StaffRegion> getListData(int grade){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "select * from tbl_staff_region where grade = "+grade+" order by id";
//		String sql = "select * from t1 where parent = 0";
		System.out.println(sql);
		List<StaffRegion> lists = new ArrayList<StaffRegion>();
		try {
			conn = DBUtil.getConnect();
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				StaffRegion region = setData(rs);
				lists.add(region);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, ps);
		}
		return lists;
	}
	
	public static List<StaffRegion> getListByParent(int parent){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "select * from tbl_staff_region where parent = "+parent+" order by id";
//		String sql = "select * from t1 where parent = 0";
		System.out.println(sql);
		List<StaffRegion> lists = new ArrayList<StaffRegion>();
		try {
			conn = DBUtil.getConnect();
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				StaffRegion region = setData(rs);
				lists.add(region);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, ps);
		}
		return lists;
	}

	public static StaffRegion setData(ResultSet rs){
		StaffRegion region = new StaffRegion();
		try {
			region.setId(rs.getInt("id"));
			region.setOrderTag(rs.getInt("order_tag"));
			region.setInvalid(rs.getInt("invalid"));
			region.setCreatedDatetime(rs.getTimestamp("created_datetime"));
			region.setGrade(rs.getInt("grade"));
			region.setParent(rs.getInt("parent"));
			region.setName(rs.getString("name"));
			region.setContent(rs.getString("content"));
			region.setCode(rs.getString("code"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return region;
	}
	
	
	
	
	
	
}
