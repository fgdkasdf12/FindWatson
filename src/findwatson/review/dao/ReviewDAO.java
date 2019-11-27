package findwatson.review.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import findwatson.configuration.Configuration;
import findwatson.review.dto.ReviewDTO;

public class ReviewDAO {
	
	private BasicDataSource bds = new BasicDataSource();
	private static ReviewDAO instance;
	
	ReviewDAO(){
		bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		bds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		bds.setUsername("watson");
		bds.setPassword("watson");
		bds.setInitialSize(10);
	}
	
	private Connection getConnection() throws Exception{
		return bds.getConnection();
	}
	public synchronized static ReviewDAO getInstance() {
		if(instance == null) {
			instance = new ReviewDAO();
		}
		return instance;
	}
	
	public int insert(ReviewDTO dto)throws Exception{ //리뷰db저장
		String sql = "insert into hosptReview values (hosptReviewSeq.nextval, ?,0,?,?,?,?,sysdate,?,0,0)";
		try(
				Connection con = getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, dto.getArticleSeq());
			pstat.setString(2, dto.getTitle());
			pstat.setString(3, dto.getContent());
			pstat.setString(4, dto.getHeader()); 
			pstat.setString(5, dto.getWriter());
			pstat.setString(6, dto.getIpAddr());
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	public int getArticleCount(int articleSeq) throws Exception{
		String sql = "select count(*) from hosptreview where articleseq = ?";
		try(
			Connection con = getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, 7777);
			try(
					ResultSet rs = pstat.executeQuery();	
					){
				rs.next();
				int result = rs.getInt(1);
				con.commit();
				return result;
			}
		}
	}
	
	public List<ReviewDTO> selectAll() throws Exception{
		String sql = "select * from hosptreview order by seq desc";
		List<ReviewDTO> list = new ArrayList<>();
		try(
			Connection con = getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
			ResultSet rs = pstat.executeQuery();
				){
			while(rs.next()) {
				//(hosptReviewSeq.nextval, 0,0,'title','content','bird','writer',sysdate,'localhost',0,0)
				int seq = rs.getInt(1);
				int articleSeq = rs.getInt(2);
				int score = rs.getInt(3);
				String title = rs.getString(4);
				String content = rs.getString(5);
				String header = rs.getString(6);
				String writer = rs.getString(7);
				Timestamp writeDate = rs.getTimestamp(8);
				String ipAddr = rs.getString(9);
				int likeCount = rs.getInt(10);
				int viewCount = rs.getInt(11);
				
				ReviewDTO dto = new ReviewDTO(seq, articleSeq, score, title, content, header, writer,
						writeDate, ipAddr, likeCount, viewCount);
				list.add(dto);
			}
			return list;
		}	
	}
	
	public List<ReviewDTO> selectByPage(int start, int end) throws Exception{
		String sql = "select * from (select row_number() over (order by seq desc)as rown, hosptreview.* from hosptreview) where rown between ? and ?";
		List<ReviewDTO> list = new ArrayList<>();
		try(
			Connection con = getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, start);
			pstat.setInt(2, end);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				while(rs.next()) {
					//(hosptReviewSeq.nextval, 0,0,'title','content','bird','writer',sysdate,'localhost',0,0)
					int seq = rs.getInt(2);
					int articleSeq = rs.getInt(3);
					int score = rs.getInt(4);
					String title = rs.getString(5);
					String content = rs.getString(6);
					String header = rs.getString(7);
					String writer = rs.getString(8);
					Timestamp writeDate = rs.getTimestamp(9);
					String ipAddr = rs.getString(10);
					int likeCount = rs.getInt(11);
					int viewCount = rs.getInt(12);
					
					ReviewDTO dto = new ReviewDTO(seq, articleSeq, score, title, content, header, writer,
							writeDate, ipAddr, likeCount, viewCount);
					list.add(dto);
				}
			}
		}	
		return list;
	}

	public String getPageNavi(int currentPage, int articleSeq)throws Exception{
		int recordTotalCount = this.getArticleCount(7777);
		
		//총 몇개의 페이지인지
		int pageTotalCount  = 0;
		int adv = recordTotalCount / Configuration.recordCountPerPage;
		
		if(adv > 0) {
			pageTotalCount = adv + 1;
		}else {
			pageTotalCount = adv;
		}
		
		//장난 막기
		if(currentPage < 1) {
			currentPage = 1;
		}else if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}
		
		//현재 내가 위치한 페이지에 따라 네비게이터 시작 페이지값 구하기
		int startNavi = (currentPage - 1)/Configuration.naviCountPerPage *Configuration.naviCountPerPage +1;
		int endNavi = startNavi + (Configuration.naviCountPerPage -1);
		
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = startNavi ; i <= endNavi ; i++) {
			sb.append("<a href='hospitalSearchDetail2.re?cpage="+i+"' id=page"+i+">");
			sb.append(i + " ");
			sb.append("</a>");
		}
		
		return sb.toString();
	}
	
	
}
