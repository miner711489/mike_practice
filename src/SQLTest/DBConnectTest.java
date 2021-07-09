/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLTest;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mike
 */
public class DBConnectTest {

    Connection connection = null;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void closeConnection() throws Exception {
        if (this.connection != null) {
            this.connection.close();
        }
    }

    public void DBConnectTestMain() throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=FSCOP;user=FSCOP;password=FSCOP;");

            this.setConnection(connection);
            this.DeleteData();
            this.insertData(100);
            this.insertData2(200);

        } catch (Exception e) {
            System.out.println("Error Message:" + e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
            this.closeConnection();
        }

    }

    public void getDBConnectMessage() {

        //<editor-fold defaultstate="collapsed" desc="comment"> 
        System.out.println("-----This is a DBTest-----");
        try {
            //<editor-fold defaultstate="collapsed" desc="DB連線範例1">
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=WRAWEBAP;user=WRAWEBAP;password=WRAWEBAP;");

            String sql = "Select * From DocHeader Where DocNO=?"; //SQL語法
            //預設建立只可以往下移動資料列的ResultSet回傳
            PreparedStatement pst = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY);
            pst.setString(1, "1090100001");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("docno")); //將結果用while印出
            }
            //</editor-fold>
            rs.close();
            pst.close();
            conn.close();
        } catch (Exception ee) {
            System.out.print(ee.getMessage());
        } finally {
        }
    }

    public void DeleteData() throws Exception {
        PreparedStatement Statement = null;

        try {
            String SQLString = "delete docheader where ModUserID=? or ModUserID=? "; //SQL語法
            //預設建立只可以往下移動資料列的ResultSet回傳
            Statement = connection.prepareStatement(SQLString, ResultSet.TYPE_FORWARD_ONLY);
            Statement.setString(1, "test");
            Statement.setString(2, "test2");
            Statement.execute();

            SQLString = "delete MoveDetail where ModUserID=? or ModUserID=? "; //SQL語法
            //預設建立只可以往下移動資料列的ResultSet回傳
            Statement = connection.prepareStatement(SQLString, ResultSet.TYPE_FORWARD_ONLY);
            Statement.setString(1, "test");
            Statement.setString(2, "test2");
            Statement.execute();

            SQLString = "delete CrossOrgHeader where ModUserID=? or ModUserID=? "; //SQL語法
            //預設建立只可以往下移動資料列的ResultSet回傳
            Statement = connection.prepareStatement(SQLString, ResultSet.TYPE_FORWARD_ONLY);
            Statement.setString(1, "test");
            Statement.setString(2, "test2");
            Statement.execute();

        } catch (Exception ee) {
            System.out.print("錯誤:" + ee.getMessage());
        } finally {
            if (Statement != null) {
                Statement.close();
            }
        }
    }

    public void insertData(int RowCnt) throws Exception {
        PreparedStatement Statement = null;

        try {
            List<Map> insertDataList = new ArrayList();
            //準備要insert的資料，3000筆
            String DocNO = "";
            String MoveDocNO = "";
            String RcvSeqNO = "";
            String RcvSeqNO2 = "";
            SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy/MM/dd");
            java.util.Date date = new java.util.Date();
            String sDate = DateFormat.format(date);
            String Year = String.valueOf(Integer.valueOf(sDate.substring(0, 4)) - 1911);
            String month = sDate.substring(5, 7);
            String day = sDate.substring(8, 10);
            for (int i = 1; i <= RowCnt; i++) {
                String SerialNO = "000000" + String.valueOf(i);
                SerialNO = SerialNO.substring(SerialNO.length() - 5, SerialNO.length());

                DocNO = Year + "81" + SerialNO;
                MoveDocNO = Year + "91" + SerialNO;
                RcvSeqNO = Year + month + "4" + SerialNO.substring(SerialNO.length() - 5, SerialNO.length());
                RcvSeqNO2 = Year + month + "5" + SerialNO.substring(SerialNO.length() - 5, SerialNO.length());
                HashMap map = new HashMap();
                map.put("DocNO", DocNO);
                map.put("MoveDocNO", MoveDocNO);
                map.put("RcvSeqNO1", RcvSeqNO);
                map.put("RcvSeqNO2", RcvSeqNO2);

                insertDataList.add(map);
            }

            List<Map> DocHeader1 = new ArrayList();
            List<Map> DocHeader2 = new ArrayList();
            List<Map> MoveDetail1 = new ArrayList();
            List<Map> MoveDetail2 = new ArrayList();
            List<Map> CrossOrgHeader1 = new ArrayList();
            List<Map> CrossOrgHeader2 = new ArrayList();

            String Select_DocHedaer = "Select * From DocHeader Where UseOrgSeqNO=? And DocNO=? "; //SQL語法
            //預設建立只可以往下移動資料列的ResultSet回傳
            Statement = connection.prepareStatement(Select_DocHedaer, ResultSet.TYPE_FORWARD_ONLY);
            Statement.setInt(1, 1);
            Statement.setString(2, "1100700001");
            try (ResultSet rs = Statement.executeQuery()) {
                DocHeader1 = getResultSetData(rs);
            }

            Statement.setInt(1, 4);
            Statement.setString(2, "1100700001");
            try (ResultSet rs = Statement.executeQuery()) {
                DocHeader2 = getResultSetData(rs);
            }

            String Select_MoveDetail = "Select * From MoveDetail Where UseOrgSeqNO=? And DocNO=? ";
            Statement = connection.prepareStatement(Select_MoveDetail, ResultSet.TYPE_FORWARD_ONLY);
            Statement.setInt(1, 1);
            Statement.setString(2, "1100700001");
            try (ResultSet rs = Statement.executeQuery()) {
                MoveDetail1 = getResultSetData(rs);
            }
            Statement.setInt(1, 4);
            Statement.setString(2, "1100700001");
            try (ResultSet rs = Statement.executeQuery()) {
                MoveDetail2 = getResultSetData(rs);
            }

            String Select_CrossOrgHeader = "Select * From CrossOrgHeader Where RcvSeqNO=? And OrgDocNO=? ";
            Statement = connection.prepareStatement(Select_CrossOrgHeader, ResultSet.TYPE_FORWARD_ONLY);
            Statement.setString(1, "11007050033");
            Statement.setString(2, "1108200001");
            try (ResultSet rs = Statement.executeQuery()) {
                CrossOrgHeader1 = getResultSetData(rs);
            }

            Statement.setString(1, "11007050035");
            Statement.setString(2, "1108200001");
            try (ResultSet rs = Statement.executeQuery()) {
                CrossOrgHeader2 = getResultSetData(rs);
            }

            for (Map Data : insertDataList) {
                if (DocHeader1.size() > 0) {
                    for (Map m : DocHeader1) {
                        if (((String) m.get("ColumnName")).toLowerCase().equals("docno")) {
                            m.put("ColumnData", (String) Data.get("DocNO"));
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("orgindocno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("DocNO"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("moduserid")) {
                            m.put("ColumnData", "Test");
                        }
                    }
                }
                if (DocHeader2.size() > 0) {
                    for (Map m : DocHeader2) {
                        if (((String) m.get("ColumnName")).toLowerCase().equals("docno")) {
                            m.put("ColumnData", (String) Data.get("DocNO"));
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("orgindocno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("DocNO"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("moduserid")) {
                            m.put("ColumnData", "Test");
                        }
                    }
                }
                if (MoveDetail1.size() > 0) {
                    for (Map m : MoveDetail1) {
                        if (((String) m.get("ColumnName")).toLowerCase().equals("docno")) {
                            m.put("ColumnData", (String) Data.get("DocNO"));
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("movdocno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("MoveDocNO"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("moduserid")) {
                            m.put("ColumnData", "Test");
                        }
                    }
                }
                if (MoveDetail2.size() > 0) {
                    for (Map m : MoveDetail2) {
                        if (((String) m.get("ColumnName")).toLowerCase().equals("docno")) {
                            m.put("ColumnData", (String) Data.get("DocNO"));
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("movdocno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("MoveDocNO"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("moduserid")) {
                            m.put("ColumnData", "Test");
                        }
                    }
                }
                if (CrossOrgHeader1.size() > 0) {
                    for (Map m : CrossOrgHeader1) {
                        if (((String) m.get("ColumnName")).toLowerCase().equals("docno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("DocNO"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("orgdocno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("MoveDocNO"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("rcvseqno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("RcvSeqNO1"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("moduserid")) {
                            m.put("ColumnData", "Test");
                        }
                    }
                }
                if (CrossOrgHeader2.size() > 0) {
                    for (Map m : CrossOrgHeader2) {
                        if (((String) m.get("ColumnName")).toLowerCase().equals("docno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("DocNO"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("orgdocno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("MoveDocNO"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("rcvseqno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("RcvSeqNO2"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("moduserid")) {
                            m.put("ColumnData", "Test");
                        }
                    }
                }

                String Insert_DocHeader = "Insert Into DocHeader  "
                        + "(UseOrgSeqNO,DocNO,OldDocNO,RcvDocNOMask,ParRcvDocNO,RegDepID,RegDepNam,RegDivID,RegDivNam,RegUserID,RegUserNam,RegTime,RcvDocuFiling,RcvDocuPages,DocSource,DocKindID,DocKindNam,FepRcvTyp,FepRcvSeqNO,AtaTyp,AtaDesc,OrgID,OrgNam,OrgDocNO,OrgSndDate,MeetingDate,DocDesc,DocTypID,DocTypNam,SpeID,SpeNam,SecID,SecNam,SecNO,UnSecID,UnSecNam,UnSecContent,UnPreSecDate,UnSecDate,CopID,CopNam,AttID,AttNam,SubAttID,SubAttNam,RpsDepID,RpsDepNam,RpsDivID,RpsDivNam,RpsUserID,RpsUserNam,RpsTime,DecMakerID,DecMakerNam,JudTyp,JudSts,CmbSts,ParDocNO,BegTime,CalculateMethod,DeadLineFrom,DueDays,UndDays,MovDays,OriginalDeadLine,Deadline,DutySts,DocOpenSts,IsTransPage,ReferDocNO,ItemLevelNO,SndDepID,SndDepNam,SndDivID,SndDivNam,SndUserID,SndUserNam,SndTime,PSndDate,SndDocNO,SndOrgDocNO,SndAltSts,SndTyp,FinDepID,FinDepNam,FinDivID,FinDivNam,FinUserID,FinUserNam,FinTime,Fintyp,FinDesc,UseDays,DelayDays,FilDeadLine,FilRcvTime,ArchiveTime,FilTime,FilDepID,FilDepNam,FilDivID,FilDivNam,FilUserID,FilUserNam,VolYear,VolClsID,CasSeqNO,VolSeqNO,FilSeqNO,RefVolClsID,DurYear,MediaTyp,DocSize,DocUnit,UseLimit,UseLimitDesc,AudSts,AudNO,AudDate,AudFDate,ExistDocDesc,OtherDocDesc,PostingContact,ArchiveContact,RemarkDesc,OrginUseOrgSeqNO,OrginDocNO,OrginOrgDate,RsvField1,RsvField2,RsvField3,RsvField4,RsvField5,CreTime,ModTime,ModUserID,ModUserNam)  "
                        + "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                Statement = setPreparedStatement(Insert_DocHeader, DocHeader1);
                if (Statement != null) {
                    Statement.executeUpdate();
                }
                Statement = setPreparedStatement(Insert_DocHeader, DocHeader2);
                if (Statement != null) {
                    Statement.executeUpdate();
                }
                String Insert_MoveDetail = "Insert Into MoveDetail  "
                        + "(UseOrgSeqNO,DocNO,MovDocNO,SndDepID,SndDepNam,SndDivID,SndDivNam,SndUserID,SndUserNam,SndTime,OrgID,OrgNam,MovTyp,NoReturn,RcvDepID,RcvDepNam,RcvDivID,RcvDivNam,RcvUserID,RcvUserNam,RcvTime,RsvField1,RsvField2,RsvField3,CreTime,ModTime,ModUserID,ModUserNam)  "
                        + "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                Statement = setPreparedStatement(Insert_MoveDetail, MoveDetail1);
                if (Statement != null) {
                    Statement.executeUpdate();
                }
                Statement = setPreparedStatement(Insert_MoveDetail, MoveDetail2);
                if (Statement != null) {
                    Statement.executeUpdate();
                }

                String Insert_CrossOrgHeader = "Insert Into CrossOrgHeader  "
                        + "(UseOrgSeqNO,RcvSeqNO,RcvOrgID,RcvOrgNam,RegTime,OrgID,OrgNam,OrgDocNO,OrgSDate,DocDesc,AtaDesc,AtaNum,SpeNam,DocTypNam,SecNam,CopNam,FileDir,SiFileNam,DiFileNam,OrgDocForViewNam,OrgDocTyp,eRcvTyp,eRcvEnCyp,ReptSts,RepDesc,TranSts,TrnDepID,TrnDepNam,TrnDivID,TrnDivNam,TrnUserID,TrnUserNam,TrnTime,DocNO,MeetingDate,DepJudSts,RsvField1,RsvField2,RsvField3,CreTime,ModTime,ModUserID,ModUserNam)  "
                        + "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                Statement = setPreparedStatement(Insert_CrossOrgHeader, CrossOrgHeader1);
                if (Statement != null) {
                    Statement.executeUpdate();
                }
                Statement = setPreparedStatement(Insert_CrossOrgHeader, CrossOrgHeader2);
                if (Statement != null) {
                    Statement.executeUpdate();
                }

                if (Statement != null) {
                    Statement.close();
                }
            }
        } catch (Exception ee) {
            System.out.print("錯誤:" + ee.getMessage());
        } finally {
            if (Statement != null) {
                Statement.close();
            }
        }
    }

    public void insertData2(int RowCnt) throws Exception {
        PreparedStatement Statement = null;

        try {
            List<Map> insertDataList = new ArrayList();
            //準備要insert的資料，300筆
            String DocNO = "";
            String MoveDocNO = "";
            String RcvSeqNO = "";
            String RcvSeqNO2 = "";
            SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy/MM/dd");
            java.util.Date date = new java.util.Date();
            String sDate = DateFormat.format(date);
            String Year = String.valueOf(Integer.valueOf(sDate.substring(0, 4)) - 1911);
            String month = sDate.substring(5, 7);
            String day = sDate.substring(8, 10);
            for (int i = 1; i <= RowCnt; i++) {
                String SerialNO = "000000" + String.valueOf(i);
                SerialNO = SerialNO.substring(SerialNO.length() - 5, SerialNO.length());

                DocNO = Year + "82" + SerialNO;
                MoveDocNO = Year + "92" + SerialNO;
                RcvSeqNO = Year + month + "6" + SerialNO.substring(SerialNO.length() - 5, SerialNO.length());
                RcvSeqNO2 = Year + month + "7" + SerialNO.substring(SerialNO.length() - 5, SerialNO.length());
                HashMap map = new HashMap();
                map.put("DocNO", DocNO);
                map.put("MoveDocNO", MoveDocNO);
                map.put("RcvSeqNO1", RcvSeqNO);
                map.put("RcvSeqNO2", RcvSeqNO2);

                insertDataList.add(map);
            }

            List<Map> DocHeader1 = new ArrayList();
            List<Map> DocHeader2 = new ArrayList();
            List<Map> MoveDetail1 = new ArrayList();
            List<Map> MoveDetail2 = new ArrayList();
            List<Map> CrossOrgHeader1 = new ArrayList();
            List<Map> CrossOrgHeader2 = new ArrayList();

            String Select_DocHedaer = "Select * From DocHeader Where UseOrgSeqNO=? And DocNO=? "; //SQL語法
            //預設建立只可以往下移動資料列的ResultSet回傳
            Statement = connection.prepareStatement(Select_DocHedaer, ResultSet.TYPE_FORWARD_ONLY);
            Statement.setInt(1, 1);
            Statement.setString(2, "1100700002");
            try (ResultSet rs = Statement.executeQuery()) {
                DocHeader1 = getResultSetData(rs);
            }

            Statement.setInt(1, 4);
            Statement.setString(2, "1100700002");
            try (ResultSet rs = Statement.executeQuery()) {
                DocHeader2 = getResultSetData(rs);
            }

            String Select_MoveDetail = "Select * From MoveDetail Where UseOrgSeqNO=? And DocNO=? ";
            Statement = connection.prepareStatement(Select_MoveDetail, ResultSet.TYPE_FORWARD_ONLY);
            Statement.setInt(1, 1);
            Statement.setString(2, "1100700002");
            try (ResultSet rs = Statement.executeQuery()) {
                MoveDetail1 = getResultSetData(rs);
            }
            Statement.setInt(1, 4);
            Statement.setString(2, "1100700002");
            try (ResultSet rs = Statement.executeQuery()) {
                MoveDetail2 = getResultSetData(rs);
            }

            String Select_CrossOrgHeader = "Select * From CrossOrgHeader Where RcvSeqNO=? And OrgDocNO=? ";
            Statement = connection.prepareStatement(Select_CrossOrgHeader, ResultSet.TYPE_FORWARD_ONLY);
            Statement.setString(1, "11007050034");
            Statement.setString(2, "1108200002");
            try (ResultSet rs = Statement.executeQuery()) {
                CrossOrgHeader1 = getResultSetData(rs);
            }

            Statement.setString(1, "11007050036");
            Statement.setString(2, "1108200002");
            try (ResultSet rs = Statement.executeQuery()) {
                CrossOrgHeader2 = getResultSetData(rs);
            }

            for (Map Data : insertDataList) {
                if (DocHeader1.size() > 0) {
                    for (Map m : DocHeader1) {
                        if (((String) m.get("ColumnName")).toLowerCase().equals("docno")) {
                            m.put("ColumnData", (String) Data.get("DocNO"));
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("orgindocno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("DocNO"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("moduserid")) {
                            m.put("ColumnData", "Test");
                        }
                    }
                }
                if (DocHeader2.size() > 0) {
                    for (Map m : DocHeader2) {
                        if (((String) m.get("ColumnName")).toLowerCase().equals("docno")) {
                            m.put("ColumnData", (String) Data.get("DocNO"));
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("orgindocno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("DocNO"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("moduserid")) {
                            m.put("ColumnData", "Test");
                        }
                    }
                }
                if (MoveDetail1.size() > 0) {
                    for (Map m : MoveDetail1) {
                        if (((String) m.get("ColumnName")).toLowerCase().equals("docno")) {
                            m.put("ColumnData", (String) Data.get("DocNO"));
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("movdocno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("MoveDocNO"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("moduserid")) {
                            m.put("ColumnData", "Test2");
                        }
                    }
                }
                if (MoveDetail2.size() > 0) {
                    for (Map m : MoveDetail2) {
                        if (((String) m.get("ColumnName")).toLowerCase().equals("docno")) {
                            m.put("ColumnData", (String) Data.get("DocNO"));
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("movdocno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("MoveDocNO"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("moduserid")) {
                            m.put("ColumnData", "Test");
                        }
                    }
                }
                if (CrossOrgHeader1.size() > 0) {
                    for (Map m : CrossOrgHeader1) {
                        if (((String) m.get("ColumnName")).toLowerCase().equals("docno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("DocNO"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("orgdocno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("MoveDocNO"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("rcvseqno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("RcvSeqNO1"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("moduserid")) {
                            m.put("ColumnData", "Test");
                        }
                    }
                }
                if (CrossOrgHeader2.size() > 0) {
                    for (Map m : CrossOrgHeader2) {
                        if (((String) m.get("ColumnName")).toLowerCase().equals("docno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("DocNO"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("orgdocno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("MoveDocNO"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("rcvseqno")) {
                            if (!((String) m.get("ColumnData")).isEmpty()) {
                                m.put("ColumnData", (String) Data.get("RcvSeqNO2"));
                            }
                        }
                        if (((String) m.get("ColumnName")).toLowerCase().equals("moduserid")) {
                            m.put("ColumnData", "Test");
                        }
                    }
                }

                String Insert_DocHeader = "Insert Into DocHeader  "
                        + "(UseOrgSeqNO,DocNO,OldDocNO,RcvDocNOMask,ParRcvDocNO,RegDepID,RegDepNam,RegDivID,RegDivNam,RegUserID,RegUserNam,RegTime,RcvDocuFiling,RcvDocuPages,DocSource,DocKindID,DocKindNam,FepRcvTyp,FepRcvSeqNO,AtaTyp,AtaDesc,OrgID,OrgNam,OrgDocNO,OrgSndDate,MeetingDate,DocDesc,DocTypID,DocTypNam,SpeID,SpeNam,SecID,SecNam,SecNO,UnSecID,UnSecNam,UnSecContent,UnPreSecDate,UnSecDate,CopID,CopNam,AttID,AttNam,SubAttID,SubAttNam,RpsDepID,RpsDepNam,RpsDivID,RpsDivNam,RpsUserID,RpsUserNam,RpsTime,DecMakerID,DecMakerNam,JudTyp,JudSts,CmbSts,ParDocNO,BegTime,CalculateMethod,DeadLineFrom,DueDays,UndDays,MovDays,OriginalDeadLine,Deadline,DutySts,DocOpenSts,IsTransPage,ReferDocNO,ItemLevelNO,SndDepID,SndDepNam,SndDivID,SndDivNam,SndUserID,SndUserNam,SndTime,PSndDate,SndDocNO,SndOrgDocNO,SndAltSts,SndTyp,FinDepID,FinDepNam,FinDivID,FinDivNam,FinUserID,FinUserNam,FinTime,Fintyp,FinDesc,UseDays,DelayDays,FilDeadLine,FilRcvTime,ArchiveTime,FilTime,FilDepID,FilDepNam,FilDivID,FilDivNam,FilUserID,FilUserNam,VolYear,VolClsID,CasSeqNO,VolSeqNO,FilSeqNO,RefVolClsID,DurYear,MediaTyp,DocSize,DocUnit,UseLimit,UseLimitDesc,AudSts,AudNO,AudDate,AudFDate,ExistDocDesc,OtherDocDesc,PostingContact,ArchiveContact,RemarkDesc,OrginUseOrgSeqNO,OrginDocNO,OrginOrgDate,RsvField1,RsvField2,RsvField3,RsvField4,RsvField5,CreTime,ModTime,ModUserID,ModUserNam)  "
                        + "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                Statement = setPreparedStatement(Insert_DocHeader, DocHeader1);
                if (Statement != null) {
                    Statement.executeUpdate();
                }
                Statement = setPreparedStatement(Insert_DocHeader, DocHeader2);
                if (Statement != null) {
                    Statement.executeUpdate();
                }
                String Insert_MoveDetail = "Insert Into MoveDetail  "
                        + "(UseOrgSeqNO,DocNO,MovDocNO,SndDepID,SndDepNam,SndDivID,SndDivNam,SndUserID,SndUserNam,SndTime,OrgID,OrgNam,MovTyp,NoReturn,RcvDepID,RcvDepNam,RcvDivID,RcvDivNam,RcvUserID,RcvUserNam,RcvTime,RsvField1,RsvField2,RsvField3,CreTime,ModTime,ModUserID,ModUserNam)  "
                        + "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                Statement = setPreparedStatement(Insert_MoveDetail, MoveDetail1);
                if (Statement != null) {
                    Statement.executeUpdate();
                }
                Statement = setPreparedStatement(Insert_MoveDetail, MoveDetail2);
                if (Statement != null) {
                    Statement.executeUpdate();
                }

                String Insert_CrossOrgHeader = "Insert Into CrossOrgHeader  "
                        + "(UseOrgSeqNO,RcvSeqNO,RcvOrgID,RcvOrgNam,RegTime,OrgID,OrgNam,OrgDocNO,OrgSDate,DocDesc,AtaDesc,AtaNum,SpeNam,DocTypNam,SecNam,CopNam,FileDir,SiFileNam,DiFileNam,OrgDocForViewNam,OrgDocTyp,eRcvTyp,eRcvEnCyp,ReptSts,RepDesc,TranSts,TrnDepID,TrnDepNam,TrnDivID,TrnDivNam,TrnUserID,TrnUserNam,TrnTime,DocNO,MeetingDate,DepJudSts,RsvField1,RsvField2,RsvField3,CreTime,ModTime,ModUserID,ModUserNam)  "
                        + "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                Statement = setPreparedStatement(Insert_CrossOrgHeader, CrossOrgHeader1);
                if (Statement != null) {
                    Statement.executeUpdate();
                }
                Statement = setPreparedStatement(Insert_CrossOrgHeader, CrossOrgHeader2);
                if (Statement != null) {
                    Statement.executeUpdate();
                }

                if (Statement != null) {
                    Statement.close();
                }
            }
        } catch (Exception ee) {
            System.out.print("錯誤:" + ee.getMessage());
        } finally {
            if (Statement != null) {
                Statement.close();
            }
        }
    }

    public List<Map> getResultSetData(ResultSet rs) throws Exception {
        List<Map> DataList = new ArrayList();

        ResultSetMetaData rsmd = rs.getMetaData();
        int ColumnCount = rsmd.getColumnCount();
        while (rs.next()) {
            for (int index = 1; index <= ColumnCount; index++) {
                int columnType = rsmd.getColumnType(index);
                String columnName = rsmd.getColumnName(index);
                Object obj = null;

                switch (columnType) {
                    case Types.INTEGER:
                        obj = rs.getInt(columnName);
                        break;
                    case Types.CHAR:
                    case Types.NVARCHAR:
                    case Types.VARCHAR:
                        obj = rs.getString(columnName);
                        break;
                    case Types.BOOLEAN:
                        obj = rs.getBoolean(columnName);
                        break;
                    case Types.DOUBLE:
                        obj = rs.getDouble(columnName);
                        break;
                    case Types.DATE:
                    case Types.TIME:
                    case Types.TIMESTAMP:
                        Timestamp stamp = rs.getTimestamp(columnName);
                        obj = new Date(stamp.getTime());
                        break;
                    default:
                        obj = rs.getString(columnName);
                        break;
                }
                HashMap map = new HashMap();
                map.put("Index", index);
                map.put("Type", columnType);
                map.put("ColumnName", columnName);
                map.put("ColumnData", obj);
                DataList.add(map);
            }
        }
        return DataList;
    }

    public PreparedStatement setPreparedStatement(String SQLString, List<Map> DataList) throws Exception {
        PreparedStatement Statement = connection.prepareStatement(SQLString, ResultSet.TYPE_FORWARD_ONLY);
        if (DataList.size() > 0) {
            for (Map m : DataList) {
                switch ((Integer) m.get("Type")) {
                    case Types.INTEGER:
                        Statement.setInt((Integer) m.get("Index"), (Integer) m.get("ColumnData"));
                        break;
                    case Types.CHAR:
                    case Types.NVARCHAR:
                    case Types.VARCHAR:
                        Statement.setString((Integer) m.get("Index"), (String) m.get("ColumnData"));
                        break;
                    case Types.BOOLEAN:
                        Statement.setBoolean((Integer) m.get("Index"), (Boolean) m.get("ColumnData"));
                        break;
                    case Types.DOUBLE:
                        Statement.setDouble((Integer) m.get("Index"), (Double) m.get("ColumnData"));
                        break;
                    case Types.DATE:
                    case Types.TIME:
                    case Types.TIMESTAMP:
                        //Timestamp stamp = rs.getTimestamp(columnName);
                        //obj = new Date(stamp.getTime());
                        //pst2.setDate((Integer) m.get("Index"), new Date((String) m.get("ColumnData")));
                        Statement.setDate((Integer) m.get("Index"), (Date) m.get("ColumnData"));
                        break;
                    default:
                        Statement.setString((Integer) m.get("Index"), (String) m.get("ColumnData"));
                        break;
                }
            }
        }

        return Statement;
    }

}
