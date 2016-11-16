# android_project
我的Android项目
## 项目简介
针对广州地区向新农村转型的农业农村，开发一个农务信息服务平台，建设内容围绕两个方面展开：第一，构建一套基于云服务的电子村务平台，主要包括 PC 端和手机端的开发，具体的功能模块有：户籍管理、村民管理、财务公示、计生管理、医保管理、报建管理、活动管理、问题反映、两代表一委员和数据统计等。将村民、村事及村委等村级元素有机关联，以便乡村管理。第二，在前者的基础上构建一套农业信息服务平台，主要包括手机端的开发，具体的功能模块有：农业资讯、农业政策、农业信息搜索等功能模块，帮助基层政府实现面向村民的“点对点”服务以及解决农务信息传递“最后一公里”的问题。

## 所实现的功能
1.村务服务模块：户籍管理，医保社保，合同管理，计划生育，报建管理，时事新闻  
2.农业信息服务模块：农业新闻展示，农业信息搜索

## 所使用的第三方SDK及API
a.小米推送  
b.友盟社会化分享  
c.友盟社区  
d.友盟更新  
e.科大讯飞语音识别

## 优化的方向
a.项目结构的优化，按照功能划分容易造成代码间的混乱，不易阅读，规范命名  
b.项目功能的增删，删除无用的jar包，图片资源，布局文件，添加注释，精简代码  
c.适配不同设备，目前很多布局文件都写得比较死，针对性太强  
d.使用一些常用的第三方框架来简化代码

### 目前遇到的瓶颈
a.如何获取后台数据库中的新闻信息并显示在单个listview的item中  
解决方法：HttpGet方法   
b.如何将后台获取到的数据逐一显示在listview中

### 8.21重要更新
a.忘记密码：上传注册账号+联系电话从后台获取密码  
b.医保缴费查询：输入用户身份证号码获取当前该用户的缴费情况

### 11.10重要更新：修复已知问题
a. 启动页更新  
b.户籍管理功能更新  
c.个人信息与账户绑定的更新

### 11.11更新：修复已知问题
a.对查看合同模板的界面进行重新设计  
b.对账号安全中的信息跟个人信息进行了统一

### 11.12更新：修复已知问题
对时事新闻界面进行了重新设计

### 11.13今日东主有喜，无更新

### 11.14项目更新日志
#### 11.14~11.20本周需要完成：  
1.民兴搜索内嵌到农业助手模块中  
2.农业助手界面需要重新设计  
3.信息查询界面需要重新设计  
4.民意征集需要完善前后台之间的交互

### 11.15 重要更新
a.民兴搜索功能已经内嵌到农业助手模块中了  
b.对信息查询界面进行了优化  
c.记住密码后可以自动登录（判定条件需要完善）

### 11.16重要更新
a.对listview的自定义设置，包括时事新闻，通讯录，RSS订阅  
b.RSS订阅功能中更新了可选择RSS源的功能（目前并不完善）
