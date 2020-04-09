package com.coco.demo;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class WkgjTest {

    private static final String AUTHORIZATION = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6ImUxMGFkYzM5NDliYTU5YWJiZTU2ZTA1N2YyMGY4ODNlTzdkeHRKRk0yQjRtJjB2JSIsImlzcyI6InhpbmdzaGVuZyIsImFjY291bnQiOiIxNTEwNzU1MTAyNSJ9.J-DJnd5S5QPg62MJeLf2-QyIGktXassL36XdnxqaJ-w";
    private static final String WID = "00000171-5cd0-bea9-0002-22e0b2e1f51f";

    /**
     * 胖子的柠檬微信
     */
    private static final String WCID_PZ_NM = "wxid_wpm8pucpl6d122";

    /**
     * 胖子的Coco机器人微信
     */
    private static final String WCID_PZ_COCO = "wxid_y4cctkgfgonr22";

    /**
     * 杜学博的猿叔叔微信
     */
    private static final String WCID_DXB_YSS = "wxid_l5534tux7elw22";

    /**
     * 杜学博的请叫我猿叔叔微信
     */
    private static final String WCID_DXB_QJWYSS = "wxid_q533j02423ny22";

    /**
     * 张琪的微信
     */
    private static final String WCID_ZQ = "wxid_7ozbnx40j66j21";

    /**
     * 张琪的微信
     */
    private static final String WCID_LHQ = "olihanqi";


    private static final String URL_GET_ALL_CONTACT = "http://134.175.73.113:8080/getAllContact";
    private static final String URL_GET_CHATROOM_MEMBER = "http://134.175.73.113:8080/getChatRoomMember";
    private static final String URL_SEND_TEXT = "http://134.175.73.113:8080/sendText";
    private static final String URL_CREATE_CHATROOM = "http://134.175.73.113:8080/createChatroom";
    private static final String URL_SHOW_IN_ADDRESS_BOOK = "http://134.175.73.113:8080/showInAddressBook";
    private static final String URL_ADD_CHATROOM_MEMBER = "http://134.175.73.113:8080/addChatRoomMember";


    public static void main(String[] args) {

//        testCreateChatroomRepeat();

        testGroupAddUser();

    }


    /**
     * 测试添加成员入群
     */
    private static void testGroupAddUser() {
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        List<String> chatroomList = getChatroomList(WID);
        chatroomList.stream().forEach(eachChatroom -> {
            log.info("给群：{} 添加成员, 此时群总数：{}， 已成功添加个数：{}， 已失败个数：{}", eachChatroom, chatroomList.size(), successCount.get(), failCount.get());
            boolean successFlag = addChatroomMember(WID, eachChatroom, WCID_LHQ);
            if (successFlag) {
                successCount.addAndGet(1);
            } else {
                failCount.addAndGet(1);
            }

            try {
                int randomInt = RandomUtil.randomInt(8, 23);
                log.info("此次休眠时间为：{} 秒", randomInt);
                Thread.sleep(randomInt * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        log.info("共计群个数：{}， 添加成员入群成功：{} 个， 添加成员入群失败：{} 个", chatroomList.size(), successCount.get(), failCount.get());
    }


    /**
     * 测试重复建群
     */
    private static void testCreateChatroomRepeat() {
        for (int i = 5; i < 1000; i++) {

            // 建群
            String topic = String.format("相亲群%s", i);
            List<String> userNameList = new ArrayList<>();
            userNameList.add("wxid_l5534tux7elw22");
            userNameList.add("wxid_y4cctkgfgonr22");

            String roomName = createChatroom(WID, topic, userNameList);
            if (null != roomName) {
                // 群保存到通讯录
                showInAddressBook(WID, roomName, true);
            }

            // 休眠3分钟
            try {
                Thread.sleep(180000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * 建群
     *
     * @return 成功返回群ID，否则false
     */
    public static String createChatroom(String wId, String topic, List<String> userNameList) {
        String roomName = null;

        try {
            JSONObject params = new JSONObject();
            params.put("wId", wId);
            params.put("topic", topic);

            StringBuilder stringBuilder = new StringBuilder();
            userNameList.stream().forEach(e -> stringBuilder.append(String.format("%s,", e)));
            params.put("userNameList", stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1));

            String result = HttpRequest.post(URL_CREATE_CHATROOM)
                    .header("Content-Type", "application/json")
                    .header("Authorization", AUTHORIZATION)
                    .body(params.toJSONString())
                    .timeout(300000)
                    .execute().body();
            log.info("API建群调用结果为：{}", result);

            JSONObject resultJsonObj = JSONObject.parseObject(result);
            Integer code = Integer.valueOf(resultJsonObj.get("code").toString());
            if (1000 == code) {
                String data = resultJsonObj.get("data").toString();
                JSONObject dataJsonObj = JSONObject.parseObject(data);
                return null == dataJsonObj.get("roomName") ? null : dataJsonObj.get("roomName").toString();
            }
        } catch (Exception e) {
            log.error("建群失败", e);
        }

        return roomName;
    }


    /**
     * 群保存到通讯录
     *
     * @param wId
     * @param chatroom
     * @param isShow
     */
    public static void showInAddressBook(String wId, String chatroom, boolean isShow) {
        try {
            JSONObject params = new JSONObject();
            params.put("wId", wId);
            params.put("chatroom", chatroom);
            params.put("isShow", isShow);

            String result = HttpRequest.post(URL_SHOW_IN_ADDRESS_BOOK)
                    .header("Content-Type", "application/json")
                    .header("Authorization", AUTHORIZATION)
                    .body(params.toJSONString())
                    .timeout(300000)
                    .execute().body();
            log.info("API群保存到通讯录调用结果为：{}", result);
        } catch (Exception e) {
            log.error("API群保存到通讯录失败，群ID为: {}", chatroom, e);
        }
    }


    /**
     * 获取指定用户的群ID集合
     *
     * @param wId
     * @return 群ID集合
     */
    public static List<String> getChatroomList(String wId) {
        List<String> chatroomList = new ArrayList<>();

        try {
            JSONObject params = new JSONObject();
            params.put("wId", wId);

            String result = HttpRequest.post(URL_GET_ALL_CONTACT)
                    .header("Content-Type", "application/json")
                    .header("Authorization", AUTHORIZATION)
                    .body(params.toJSONString())
                    .timeout(300000)
                    .execute().body();
            log.info("API获取联系人列表结果为：{}", result);

            JSONObject resultJsonObj = JSONObject.parseObject(result);
            Integer code = Integer.valueOf(resultJsonObj.get("code").toString());
            if (1000 != code) {
                log.error("API获取联系人列表失败，WID为: {}", wId);
                return chatroomList;
            }

            String dataJsonStr = resultJsonObj.get("data").toString();
            JSONObject dataJsonObj = JSONObject.parseObject(dataJsonStr);
            JSONArray groupJsonArray = (JSONArray) dataJsonObj.get("group");
            groupJsonArray.stream().forEach(eachGroup -> {
                String userName = ((JSONObject) eachGroup).get("userName").toString();
                chatroomList.add(userName);
            });


        } catch (Exception e) {
            log.error("API获取联系人列表失败，WID为: {}", wId, e);
        }

        return chatroomList;
    }


    /**
     * 添加群成员
     *
     * @param wId
     * @param chatroom
     * @param wcId
     * @return 成功返回true, 否则false
     */
    public static boolean addChatroomMember(String wId, String chatroom, String wcId) {
        boolean successFlag = false;

        try {
            JSONObject params = new JSONObject();
            params.put("wId", wId);
            params.put("chatroom", chatroom);
            params.put("wcId", wcId);

            String result = HttpRequest.post(URL_ADD_CHATROOM_MEMBER)
                    .header("Content-Type", "application/json")
                    .header("Authorization", AUTHORIZATION)
                    .body(params.toJSONString())
                    .timeout(300000)
                    .execute().body();
            log.info("API添加群成员结果为：{}", result);

            JSONObject resultJsonObj = JSONObject.parseObject(result);
            Integer code = Integer.valueOf(resultJsonObj.get("code").toString());
            successFlag = 1000 == code;

        } catch (Exception e) {
            log.error("API添加群成员失败，WID为: {}", wId, e);
        }

        return successFlag;
    }


}
