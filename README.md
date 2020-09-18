# 如何搞一个粗略的TicTocToe

> 只是强行实现,未考虑各种设计原则和模式,比较乱.后期可能重新实现,作为对比学习.

TODO: god.hu.GamePanel UI 和 逻辑分离,目前整合在一块 

- 游戏面板:
  - [x] 九宫格
  - [x] 标记
  - [x] 绘制button图案
  - [x] button结束时颜色
  - [x] button胜利闪烁
  - [x] 选择模式(本地离线--双端对战)
    - [ ] ~~判断是否中途点击,冲突检测~~(未实现)
    - [ ] ~~添加机器人~~(未实现)
  - [ ] TODO:选择标记,先手选择
  - [x] ~~日志系统~~(不完美实现)
    - [x] autoscroll 自动滚动,(可带拖拽条),理想情况是不带拖拽条,自动刷新页面
      - > `setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);`控制不显示拖拽条,`setAutoscrolls(true)`自动滚动
    - [ ] TODO:打印的数据清理问题,防止溢出,及时清理滚动出的内容
  
- 裁判系统
  - [x] 谁输谁赢~~
  - [x] 重开
  - [x] 同时多个达成判断
    - [x] 多个timer并发消除
  
- [ ] 多端联机
  - [x] 基本服务器-客户端通信
  - [x] 客户端-服务器-客户端 转发数据
    - > [copy](https://www.codejava.net/java-se/networking/how-to-create-a-chat-console-application-in-java-using-socket)
  - [x] 双端同步
  - [ ] 结束时延迟重置,防止冲突(锁定)
  - [x] 步骤锁,防止同时点击
    - [ ] TODO:开局时候先手判断未锁,未解决
  - [ ] 处理网络问题,比如没发送成功,延迟等,成功验证
  - [ ] `important`客户端连接加入标识,可同时输入标识码对战.
  - [ ] `important`TODO:全局异步处理,避免网络延迟导致的步骤乱序,加入异步等待动画
  
- 完美化
  - [ ] 接口化,自定义宿主机和服务器客户端等配置
  - [ ] 代码封装完美
  - [ ] 类编写完美
  - [ ] 各类关系条理清晰
  - [ ] 其他问题

- 希望的特性(待实现)
  - [ ] 在线匹配,多人对战
  - [ ] 观测模式
  - [ ] 聊天

- TODO: & BUGs:
  - [ ] list溢出
  - [ ] 内存泄露
  - [ ] 网络连接线程执行过多
  
```java
        java.lang.IndexOutOfBoundsException: Index: 3, Size: 0
        at java.util.ArrayList.rangeCheck(ArrayList.java:657)
        at java.util.ArrayList.get(ArrayList.java:433)
        at god.hu.GamePanel.getStr(god.hu.GamePanel.java:405)
        at god.hu.GamePanel.judge(god.hu.GamePanel.java:339)
        at god.hu.GamePanel.decision(god.hu.GamePanel.java:494)
        at god.hu.GamePanel$1.run(god.hu.GamePanel.java:125)
        at java.lang.Thread.run(Thread.java:748)
```

- 在经过后期学习各种优秀设计原理和原则,以及设计模式等后,希望可以改装,重新实现,作为对比.
