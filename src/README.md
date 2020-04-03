# 如何搞一个粗略的TicTocToe

- 游戏面板:
  - [x] 九宫格
  - [x] 标记
  - [x] 绘制button图案
  - [x] button结束时颜色
  - [x] button胜利闪烁
  - [ ] 选择模式(本地离线--双端对战),~~或者添加机器人~~
  - [ ] 选择标记,先手选择
  - [x] ~~日志系统~~(不完美实现)
  
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
  
- 完美化
  - [ ] 接口化,自定义宿主机和服务器客户端等配置
  - [ ] 代码封装完美
  - [ ] 类编写完美
  - [ ] 各类关系条理清晰
  - [ ] 其他问题
