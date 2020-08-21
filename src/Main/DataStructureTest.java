/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Mike
 */
public class DataStructureTest {

    public String GsonTestPrintf() throws Exception {

        try {
            String JsonString = "[{\"fileFunc\":\"OF\",\"fileNam\":\"1080028227e_1_di.pdf\"},{\"fileFunc\":\"OF\",\"fileNam\":\"1080028227e-1-0.pdf\"},{\"fileFunc\":\"OF\",\"fileNam\":\"1080028227e-1-1.pdf\"},{\"fileFunc\":\"OF\",\"fileNam\":\"1080028227e-1-2.pdf\"},{\"fileFunc\":\"OP\",\"fileNam\":\"附件-----測試電子附件.txt\"},{\"fileFunc\":\"OP\",\"fileNam\":\"測試參考資料.pdf\"}]";
            JsonArray Ata = new Gson().fromJson(JsonString, JsonArray.class);
            for (JsonElement temp : Ata) {
                String FileName = temp.getAsJsonObject().get("fileNam").getAsString().trim();
                String FileDir = temp.getAsJsonObject().get("fileFunc").getAsString().trim();
                System.out.println(FileName + ":" + FileDir);
            }
            System.out.println();
            for (int i = 0; i < Ata.size(); i++) {
                String FileName = Ata.get(i).getAsJsonObject().get("fileNam").toString();//取得檔名
                String FileDir = Ata.get(i).getAsJsonObject().get("fileFunc").toString();//取得路徑
                System.out.println(FileName + ":" + FileDir);

            }
        } catch (Exception e) {
            throw e;
        }
        return "";
    }

    public void JsonTest() throws Exception {

        try {
            String JsonString = "[{\"fileFunc\":\"OF\",\"fileNam\":\"1080028227e_1_di.pdf\"},{\"fileFunc\":\"OF\",\"fileNam\":\"1080028227e-1-0.pdf\"},{\"fileFunc\":\"OF\",\"fileNam\":\"1080028227e-1-1.pdf\"},{\"fileFunc\":\"OF\",\"fileNam\":\"1080028227e-1-2.pdf\"},{\"fileFunc\":\"OP\",\"fileNam\":\"附件-----測試電子附件.txt\"},{\"fileFunc\":\"OP\",\"fileNam\":\"測試參考資料.pdf\"}]";
            JsonArray Ata = new Gson().fromJson(JsonString, JsonArray.class);

            String JsonString2 = "[{\"index\":0,\"content\":\"第0次\"},{\"index\":1,\"content\":\"第1次\"},{\"index\":2,\"content\":\"第2次\"},{\"index\":3,\"content\":\"第3次\"},{\"index\":4,\"content\":\"第4次\"},{\"index\":5,\"content\":\"第5次\"},{\"index\":6,\"content\":\"第6次\"},{\"index\":7,\"content\":\"第7次\"},{\"index\":8,\"content\":\"第8次\"},{\"index\":9,\"content\":\"第9次\"}]";

            JsonArray jsonArray = new JsonArray();
            JsonObject jsonObject = new JsonObject();

            for (int i = 0; i < 10; i++) {
                jsonObject = new JsonObject();
                jsonObject.addProperty("index", i);
                jsonObject.addProperty("content", "第" + String.valueOf(i) + "次");
                jsonArray.add(jsonObject);
            }

            JsonObject bigJsonObject = new JsonObject();
            bigJsonObject.add("A", jsonArray);

            JsonArray bigJsonArray = new JsonArray();

            bigJsonArray.add(bigJsonObject);

            System.out.println(bigJsonObject.toString());

        } catch (Exception e) {
            throw e;
        }

    }

    public void PrintfJsonObject(String JsonString) throws Exception {

        try {

            JsonObject J = new Gson().fromJson(JsonString, JsonObject.class);

            J.get("retCode");

            JsonObject retVal = new Gson().fromJson(J.get("retVal").toString(), JsonObject.class);

            System.out.println(retVal.getAsJsonObject("0001"));

        } catch (Exception e) {
            throw e;
        }

    }

    public void ArrayListTest() throws Exception {
        try {
            ArrayList al = new ArrayList();
            Queue<String> aa = new LinkedList<String>();
            for (int i = 0; i < 10; i++) {
                al.add(i);
                aa.add("第" + String.valueOf(i) + "次");
                //al.add(i, "第" + String.valueOf(i) + "次");
            }
            System.out.println(al.toString() + aa.toString());
        } catch (Exception e) {
            throw e;
        }
    }

    public void QueueTest() throws Exception {
        try {
            Queue queue = new LinkedList();
            for (int i = 0; i < 15; i++) {
                queue.add("第" + String.valueOf(i) + "次");
            }
            System.out.println(queue.toString());
            queue.poll();
            System.out.println(queue.toString());
            queue.poll();
            System.out.println(queue.toString());
            queue.poll();
            System.out.println(queue.toString());
            queue.poll();
            System.out.println(queue.toString());
            queue.poll();
            System.out.println(queue.toString());
            queue.poll();
            System.out.println(queue.toString());
            queue.poll();
            System.out.println(queue.toString());
            System.out.println(queue.peek());
            System.out.println(queue.toString());
            queue.offer("aa");
            System.out.println(queue.toString());

        } catch (Exception e) {
            throw e;
        }
    }

    public void StackTest() throws Exception {
        try {
            Stack stack = new Stack();
            Stack stack2 = new Stack();

            for (int i = 1; i <= 15; i++) {
                stack.add("第" + String.valueOf(i) + "次");
            }
            System.out.println(stack.toString());
            System.out.println(stack.peek());
            System.out.println(stack.toString());
            System.out.println(stack.pop());
            System.out.println(stack.toString());

        } catch (Exception e) {
            throw e;
        }
    }
}
