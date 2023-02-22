package com.lemur.chat.view;

import com.lemur.chat.service.impl.FileClientService;
import com.lemur.chat.service.impl.MessageClientService;
import com.lemur.chat.service.impl.UserClientService;
import com.lemur.chat.utils.Utility;

/**
 * 客户端菜单界面
 */
public class UserClientView {

    //控制是否显示菜单
    private boolean loop = Boolean.TRUE;

    //接收用户的键盘输入
    private String key = "";

    //用于登录服务，注册用户
    private final UserClientService userClientService = new UserClientService();

    //用于消息的交互
    private final MessageClientService messageClientService = new MessageClientService();

    //用于文件交互
    private final FileClientService fileClientService = new FileClientService();

    //显示主菜单
    public UserClientView() {
        while (loop) {
            System.out.println("==========欢迎登录网络通信系统==========");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 9 退出系统");
            System.out.println("请输入你的选择：");

            key = Utility.readString(1);

            //根据用户的输入，来处理不同的逻辑
            switch (key) {
                case "1":
                    System.out.print("请输入用户名：");
                    String userName = Utility.readString(50);
                    System.out.print("请输入密  码：");
                    String pwd = Utility.readString(50);
                    //需要到服务端验证该用户是否合法
                    if (userClientService.checkUser(userName, pwd)) {
                        System.out.println("==========欢迎" + userName + "进入网络通信系统==========");
                        while (loop) {
                            System.out.println("\n==========网络通信系统二级菜单(用户" + userName + ")==========");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");

                            System.out.println("请输入你的选择：");

                            key = Utility.readString(1);

                            //根据用户的输入，来处理不同的逻辑
                            switch (key) {
                                case "1":
                                    //System.out.println("显示在线用户列表");
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    //System.out.println("群发消息");
                                    System.out.println("发送群聊消息：");
                                    String content = Utility.readString(500);
                                    messageClientService.sendMessageToAll(content, userName);
                                    break;
                                case "3":
                                    //System.out.println("私聊消息");
                                    System.out.print("请输入用户名(在线)：");
                                    String receiver = Utility.readString(50);
                                    System.out.println("发送消息：");
                                    content = Utility.readString(500);
                                    messageClientService.sendMessageToOne(content, userName, receiver);
                                    break;
                                case "4":
                                    System.out.print("请输入用户名(在线)：");
                                    receiver = Utility.readString(50);
                                    System.out.println("请输入需要发送的文件路径：");
                                    String src = Utility.readString(100);
                                    System.out.println("请输入需要保存到的文件路径：");
                                    String dest = Utility.readString(100);
                                    fileClientService.sendFileToOne(src, dest, userName, receiver);
                                    //System.out.println("发送文件");
                                    break;
                                case "9":
                                    userClientService.logout();
                                    loop = Boolean.FALSE;
                                    break;
                                default:
                                    System.out.println("未识别指令，请重新选择");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("账号或密码错误，请重新登录");
                    }
                    break;
                case "9":
                    loop = Boolean.FALSE;
                    System.out.println("退出系统");
                    break;
                default:
                    System.out.println("未识别指令，请重新选择");
                    break;
            }
        }

    }
}
