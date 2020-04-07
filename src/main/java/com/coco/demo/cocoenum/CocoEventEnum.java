package com.coco.demo.cocoenum;

public enum CocoEventEnum {

    /**
     * JoinChatroom-机器人收到进群邀请（邀请机器人进群）
     */
    JOIN_CHATROOM("JoinChatroom", "机器人收到进群邀请（邀请机器人进群）"),

    /**
     * PushLoginUrl-其它设备需要登录
     */
    PUSH_LOGIN_URL("PushLoginUrl", "机器人收到进群邀请（邀请机器人进群）"),

    /**
     * ReceiveSysMsg-系统消息，包括谁改了群名、谁加入群了、谁退出群了等
     */
    RECEIVE_SYS_MSG("ReceiveSysMsg", "系统消息，包括谁改了群名、谁加入群了、谁退出群了等"),

    /**
     * ReceiveVerifyMsg-加机器人为好友，需要机器人确认通过
     */
    RECEIVE_VERIFY_MSG("ReceiveVerifyMsg", "加机器人为好友，需要机器人确认通过"),

    /**
     * ReceiveTransferMsg-收到转账，需要自己调用方法确认收款
     */
    RECEIVE_TRANSFER_MSG("ReceiveTransferMsg", "收到转账，需要自己调用方法确认收款"),


    /**
     * ReceiveQRPayMsg-收到二维码付款
     */
    RECEIVE_QR_PAY_MSG("ReceiveQRPayMsg", "收到二维码付款"),

    /**
     * ReceiveHBMsg-收到红包消息，需要自己调用收红包才能收到
     */
    RECEIVE_HB_MSG("ReceiveHBMsg", "收到红包消息，需要自己调用收红包才能收到"),

    /**
     * ReceiveMessage-收到消息，最复杂的一个事件，微信很多事情都用这个来通知，框架解析了一些，如果有需要解析的，可以再反馈。
     */
    RECEIVE_MESSAGE("ReceiveMessage", "收到消息，最复杂的一个事件，微信很多事情都用这个来通知，框架解析了一些，如果有需要解析的，可以再反馈。"),

    ;

    private String key;
    private String value;

    CocoEventEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    /**
     * 根据整型索引得到对应的名称
     *
     * @param key
     * @return value
     */
    public static CocoEventEnum getCocoEventEnum(String key) {
        for (CocoEventEnum e : CocoEventEnum.values()) {
            if (e.key.equals(key)) {
                return e;
            }
        }
        return null;
    }


    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
