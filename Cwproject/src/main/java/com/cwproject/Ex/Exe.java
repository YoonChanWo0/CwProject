package com.cwproject.Ex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
import com.cwproject.db.Account;
import com.cwproject.db.Customer;
import com.mysql.cj.protocol.Resultset;

public class Exe {
			
	    // ArrayList 컬랙션 생성
	    static List<Account> accountList = new ArrayList<>(); // 계좌번호, 비밀번호 리스트
	    static List<Customer> customerList = new ArrayList<>(); // 계좌번호 이름 찾는 리스트
	    static Scanner scanner = new Scanner(System.in);
	
	    public static void main(String[] args) {
	    	
	    	// 변수 선언
			Connection conn = null; // db연결
			Statement st = null; 
			ResultSet rs = null;
		
	    	
			try {
			    Class.forName("com.mysql.cj.jdbc.Driver");  // 수정된 드라이버 클래스 이름
			    System.out.println("MySQL 드라이버 로드 성공!");
			    String url = "jdbc:mysql://localhost:3306/cwproject";  // 수정된 URL
			    String user = "root";
			    String pw = "****";
			    String sql = "SELECT * FROM Account";
			    
			    conn = DriverManager.getConnection(url, user, pw);
			    System.out.println("DB 연결 성공");
			    
			    st = conn.createStatement();
			    rs = st.executeQuery(sql);
			    
			} catch (ClassNotFoundException | SQLException e) {
			    e.printStackTrace();
			} finally {
			    try {
			        if(rs != null) rs.close();
			        if(st != null) st.close();
			        if(conn != null) conn.close();
			    } catch (SQLException e) {
			        // TODO: handle exception
			    }
			}
	    	
			while (true) {
			    try {
			        printMenu();
			        String input = scanner.nextLine();  // 문자열로 입력 받기
			        int choice = Integer.parseInt(input);  // 문자열을 정수로 변환

			        switch (choice) {
			        case 1:
			            makeacc();
			            break;
			        case 2:
			            balance();
			            break;
			        case 3:
			            deposit();
			            break;
			        case 4:
			            withdraw();
			            break;
			        case 5:
			        	accountview();
			        	break;
			        case 6:
			            System.out.println("프로그램을 종료합니다.");
			            return;
			       
			        default:
			            System.out.println("1~6 사이의 숫자를 입력해주세요.");
			            break;
			        }
			    } catch (NumberFormatException e) {
			        System.out.println("잘못된 값입니다. 숫자만 입력해주세요.");
			    } catch (Exception e) {
			        System.out.println("알 수 없는 오류가 발생했습니다. 프로그램을 재시작해주세요.");
			    }
			}

	    }
	
	    private static void printMenu() {
	        System.out.println("**** 메뉴 ****");
	        System.out.println("1. 계좌 등록");
	        System.out.println("2. 계좌 확인");
	        System.out.println("3. 입금");
	        System.out.println("4. 출금");
	        System.out.println("5. 모든 계좌 출력");
	        System.out.println("6. 종료");
	        System.out.print("메뉴를 선택하세요: ");
	    }   
	
	    // 계좌등록
	    private static void makeacc() {
	        System.out.println("계좌등록하기를 선택하셨습니다.");
	        System.out.println("이름을 입력하세요.");
	        String name = scanner.nextLine();
	        // 이름에 숫자 예외처리
	        if (name.matches(".*\\d+.*")) {
	        	System.out.println("이름에 숫자가 포함될 수 없습니다. 다시 시도해 주세요.");
	        	return;
	        }
	        System.out.println("주민번호를 입력하세요.");
	        String snumber = scanner.nextLine();
	        // 주민등록번호를 입력받은 후에 패턴에 맞는지 확인
            if (!snumber.matches("\\d{6}-\\d{6}")) {
                System.out.println("주민등록번호 형식이 올바르지 않습니다. 다시 입력해주세요.");
                return;
            }
	        String anumber = RandomAccountNumber();
	        System.out.println("사용하실 비밀번호를 입력하세요");
	        int password = scanner.nextInt();
	        scanner.nextLine(); // 입력 버퍼 클리어
	        System.out.println("폰번호를 입력하세요.");
	        int phonenum = scanner.nextInt();
	        // 폰번호 11자리 맞는지 확인 --> db에서 데이터 타입을 int형으로 해서 밑의 정규식 코드 실행 불가.
//	        if (!snumber.matches("\\d+")) {
//                System.out.println("휴대폰 번호 형식이 올바르지 않습니다. 다시 입력해주세요.");
//                return;
//            }
//	        if (phonenum.length() != 11 || !phonenum.matches("\\d{11}")) {
//	            System.out.println("휴대폰 번호 형식이 올바르지 않습니다. 다시 입력해주세요.");
//	            return;
//	        }
	     // 폰번호 11자리 맞는지 확인
//	        if (String.valueOf(phonenum).length() != 11) {
//	            System.out.println("폰번호는 11자리여야 합니다. 다시 입력해주세요.");
//	            return;
//	        }
	        scanner.nextLine(); // 입력 버퍼 클리어
	        System.out.println("성별을 입력하세요");
	        String gender = scanner.nextLine();
	        if (!gender.equals("남자") && !gender.equals("여자") && !gender.equals("남")&& !gender.equals("여")) {
	            System.out.println("올바른 성별을 입력해주세요. (남자 & 여자 & 남 & 여)");
	            return;
	        }
	        Connection conn = null; // db 연결
	        PreparedStatement pstmt = null; // 작성한 쿼리문을 'pstmt'에 할당
	        ResultSet rs = null; // 결과를 rs에 저징
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL JDBC 드라이버를 로드(등록) : 자바와 db를 연결하기 위해서 dbms에 정보(경로)를 주는것이라고 생각하기
	            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cwproject", "root", "****");

	            // 주민번호 중복 예외처리
	            String checkQuery = "SELECT count(*) FROM Customer WHERE snumber = ?";
	            pstmt = conn.prepareStatement(checkQuery);
	            pstmt.setString(1, snumber);
	            rs = pstmt.executeQuery();

	            if (rs.next() && rs.getInt(1) > 0) {
	                System.out.println("주민등록번호가 중복되었습니다. 다시 시도해주세요.");
	                return;
	            }
	            
	            // 휴대폰 번호 중복 예외처리
	            String intphone = "SELECT count(*) FROM Customer WHERE phonenum = ?"; 
	            pstmt = conn.prepareStatement(intphone);
	            pstmt.setLong(1, phonenum);
	            rs = pstmt.executeQuery();
	            
	            if(rs.next() && rs.getInt(1) > 0) {
	            	System.out.println("휴대폰 번호가 중복되었습니다. 다시 시도해주세요.");
	            	return;
	            }
	            
	            // Customer 정보 등록
	            String insertCustomerQuery = "INSERT INTO Customer (name, snumber, phonenum, gender) VALUES (?, ?, ?, ?)";
	            pstmt = conn.prepareStatement(insertCustomerQuery, Statement.RETURN_GENERATED_KEYS); // 새로 추가된 행의 자동 증가 키를 얻는 코드
	            pstmt.setString(1, name);
	            pstmt.setString(2, snumber);
	            pstmt.setInt(3, phonenum);
	            pstmt.setString(4, gender);
	            int customerAffected = pstmt.executeUpdate();

	            // Customer ID 가져오기
	            long customer_id = 0;
	            if (customerAffected > 0) {
	                rs = pstmt.getGeneratedKeys();
	                if (rs.next()) {
	                	customer_id = rs.getLong(1);
	                }
	            }
	            
	            // Account 정보 등록
	            String insertAccountQuery = "INSERT INTO Account (customer_id, anumber, password) VALUES (?, ?, ?)";
	            pstmt = conn.prepareStatement(insertAccountQuery);
	            pstmt.setLong(1, customer_id);
	            pstmt.setString(2, anumber);
	            pstmt.setInt(3, password);
	            int accountAffected = pstmt.executeUpdate();

	            if (accountAffected > 0) {
	                System.out.println("계좌가 등록되었습니다.");
	                System.out.println("당신의 계좌 번호는 " + anumber + " 입니다.");
	            } else {
	                System.out.println("계좌 등록 실패.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (rs != null) rs.close();
	                if (pstmt != null) pstmt.close();
	                if (conn != null) conn.close();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	    
	    
	    // 계좌 확인
	    private static void balance() {
	        System.out.println("계좌 확인을 선택하셨습니다.");
	        System.out.println("계좌번호를 입력하세요.");
	        String accountNumber = scanner.nextLine();
	        System.out.println("비밀번호를 입력하세요.");
	        int password = scanner.nextInt();
	        scanner.nextLine(); // 입력 버퍼 클리어

	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        
	        try {
	        	// DB연결
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cwproject", "root", "****");
				
				// SQL쿼리 실행
				String query = "SELECT Customer.name, Account.anumber, Account.balance " +
			               "FROM Account INNER JOIN Customer ON Account.customer_id = Customer.customer_id " +
			               "WHERE Account.anumber = ? AND Account.password = ?";

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, accountNumber); // 첫 번째 매개변수 1에는 입력받은 계좌번호를 설정
				pstmt.setInt(2, password); //  두 번째 매개변수 2에는 입력받은 비밀번호를 설정
				rs = pstmt.executeQuery(); // executeQuery() 메서드를 호출하여 쿼리를 실행하고, 결과를 rs에 저장
				
				// 결과처리
				if(rs.next()) { // 결과셋이 있다면
					String name = rs.getString("name");
					String anumber = rs.getString("anumber");
					int balance = rs.getInt("balance");
					System.out.println("이름 : " + name);
					System.out.println("계좌번호 : " + anumber);
					System.out.println("잔액 : " + balance); // 이까지 호출
				}else {
					System.out.println("계좌번호 또는 비밀번호가 일치하지 않습니다."); // 없으면 이거 호출
				}
				
			} catch (SQLException e) {
				System.out.println("계좌확인 메서드 오류 발생 비밀번호를 다시 확인해 보세요~"+ e.getMessage()); // 예외처리
				
			}finally {
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (SQLException e) {
					System.out.println("계좌확인 DB연결 해제 중 오류 발생" + e.getMessage());// 예외처리
				}
			}
	    }
    
	    // 입금
		private static void deposit() {
		    System.out.println("입금하기를 선택하셨습니다.");
		    while (true) {
		        System.out.println("계좌번호를 입력하세요.");
		        String accountNumber = scanner.nextLine();
		        System.out.println("비밀번호를 입력하세요.");
		        int password = scanner.nextInt();
		        scanner.nextLine(); // 입력 버퍼 클리어

		        Connection conn = null;
		        PreparedStatement pstmt = null;
		        ResultSet rs = null;

		        try {
		            // DB 연결
		            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cwproject", "root", "****");

		            // 계좌번호와 비밀번호 확인
		            String query = "SELECT balance FROM Account WHERE anumber = ? AND password = ?";
		            pstmt = conn.prepareStatement(query);
		            pstmt.setString(1, accountNumber);
		            pstmt.setInt(2, password);
		            rs = pstmt.executeQuery();

		            if (rs.next()) {
		                int Cbalance = rs.getInt("balance");
		                System.out.println("입금할 금액을 입력하세요.");
		                int money = scanner.nextInt();
		                scanner.nextLine();  // 입력 버퍼 클리어

		                if (money < 0) {
		                    System.out.println("음수는 입력할 수 없습니다.");
		                    continue; // 다시 금액 입력
		                }

		                // 입금 금액 업데이트
		                int newBalance = Cbalance + money;
		                query = "UPDATE Account SET balance = ? WHERE anumber = ? AND password = ?";
		                pstmt = conn.prepareStatement(query);
		                pstmt.setInt(1, newBalance);
		                pstmt.setString(2, accountNumber);
		                pstmt.setInt(3, password);
		                int Edeposit = pstmt.executeUpdate();

		                if (Edeposit > 0) {
		                    System.out.println("입금이 완료되었습니다. 잔액: " + newBalance);
		                } else {
		                    System.out.println("입금 실패, 계좌 정보를 확인해 주세요.");
		                }
		                break; // 입금 성공 시 반복문 종료
		            } else {
		                System.out.println("계좌번호 또는 비밀번호가 잘못되었습니다. 다시 시도해주세요.");
		                break; // 잘못된 계좌번호 또는 비밀번호 입력 시 반복문 종료
		            }
		        } catch (SQLException e) {
		            System.out.println("데이터베이스 연결 또는 쿼리 실행 오류: " + e.getMessage());
		            e.printStackTrace();
		        } catch (InputMismatchException e) {
		            System.out.println("잘못된 금액 형식입니다. 숫자를 다시 입력하여 주세요.");
		            scanner.nextLine(); // 입력 버퍼 클리어 후 다시 금액 입력
		        } finally {
		            try {
		                if (rs != null) rs.close();
		                if (pstmt != null) pstmt.close();
		                if (conn != null) conn.close();
		            } catch (SQLException e) {
		                System.out.println("DB 연결 해제 중 오류 발생: " + e.getMessage());
		            }
		        }
		    }
		}


	    // 출금
		private static void withdraw() {
		    System.out.println("출금하기를 선택하셨습니다.");
		    while (true) {
		        System.out.println("계좌번호를 입력하세요.");
		        String accountNumber = scanner.nextLine();
		        System.out.println("비밀번호를 입력하세요.");
		        int password = scanner.nextInt();
		        scanner.nextLine(); // 입력 버퍼 클리어

		        Connection conn = null;
		        PreparedStatement pstmt = null;
		        ResultSet rs = null;

		        try {
		            // DB 연결
		            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cwproject", "root", "****");

		            // 계좌번호와 비밀번호 확인
		            String query = "SELECT balance FROM Account WHERE anumber = ? AND password = ?";
		            pstmt = conn.prepareStatement(query);
		            pstmt.setString(1, accountNumber);
		            pstmt.setInt(2, password);
		            rs = pstmt.executeQuery();

		            if (rs.next()) {
		                int Cbalance = rs.getInt("balance");
		                System.out.println("출금할 금액을 입력하세요.");
		                int money = scanner.nextInt();
		                scanner.nextLine();  // 입력 버퍼 클리어

		                if (money < 0) {
		                    System.out.println("음수는 입력할 수 없습니다.");
		                    continue; // 다시 금액 입력
		                }
		                
		                if (money > Cbalance) {
		                    System.out.println("잔액이 부족힙니다. 출금액이 계좌의 잔액을 초과하였습니다.");
		                    continue; // 출금 금액 초과, 다시 반복
		                }

		                // 출금 금액 업데이트
		                int newBalance = Cbalance - money;
		                query = "UPDATE Account SET balance = ? WHERE anumber = ? AND password = ?";
		                pstmt = conn.prepareStatement(query);
		                pstmt.setInt(1, newBalance);
		                pstmt.setString(2, accountNumber);
		                pstmt.setInt(3, password);
		                int Ewithdraw = pstmt.executeUpdate();

		                if (Ewithdraw > 0) {
		                    System.out.println("출금이 완료되었습니다. 잔액: " + newBalance);
		                } else {
		                    System.out.println("출금 실패, 계좌 정보를 확인해 주세요.");
		                }
		                break; // 출금 성공 시 반복문 종료
		            } else {
		                System.out.println("계좌번호 또는 비밀번호가 잘못되었습니다. 다시 시도해주세요.");
		                break; // 잘못된 계좌번호 또는 비밀번호 입력 시 반복문 종료
		            }
		        } catch (SQLException e) {
		            System.out.println("데이터베이스 연결 또는 쿼리 실행 오류: " + e.getMessage());
		            e.printStackTrace();
		        } catch (InputMismatchException e) {
		            System.out.println("잘못된 금액 형식입니다. 숫자를 다시 입력하여 주세요.");
		            scanner.nextLine(); // 입력 버퍼 클리어 후 다시 금액 입력
		        } finally {
		            try {
		                if (rs != null) rs.close();
		                if (pstmt != null) pstmt.close();
		                if (conn != null) conn.close();
		            } catch (SQLException e) {
		                System.out.println("DB 연결 해제 중 오류 발생: " + e.getMessage());
		            }
		        }
		    }
		}
		
		// 모든 계좌 확인
		public static void accountview(){
			System.out.println("모든계좌를 확인합니다.");
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cwproject", "root", "****");
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT Account.anumber, Account.balance, Customer.name " +
	                       "FROM Account INNER JOIN Customer ON Account.customer_id = Customer.customer_id");
				System.out.println("==== 전체 계좌 목록 ====");
				while(rs.next()) {
					String anumber = rs.getString("anumber");
					int balance = rs.getInt("balance");
					String name = rs.getString("name");
					System.out.println("계좌번호 : "+anumber+" , 이름 : "+name+" , 잔액 :"+balance);
				}
			} catch (SQLException e) {
				System.out.println("계좌 조회 중 오류 발생!!!! "+e.getMessage());
			}finally {
					try {
						if(rs != null)rs.close();
						if(stmt != null)stmt.close();
						if(conn != null)conn.close();
					} catch (SQLException e) {
					System.out.println("DB연결 해제 중 오류 발생!!!"+e.getMessage());
					}
			}
			
		}
		
		 // 랜덤한 형식의 계좌번호 생성 메서드 (3자리-4자리-4자리-2자리)
	    private static String RandomAccountNumber() {
	        Random random = new Random();
	        return MRD(random, 3) + "-" +
	        	   MRD(random, 4) + "-" +
	        	   MRD(random, 4) + "-" +
	        	   MRD(random, 2);
	    }

	    // 주어진 길이의 무작위 숫자 문자열을 생성하는 메서드
	    private static String MRD(Random random, int length) {
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < length; i++) {
	            sb.append(random.nextInt(10)); // 0에서 9 사이의 랜덤 숫자 생성
	        }
	        return sb.toString();
	    }

	}
	
