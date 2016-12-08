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
a.对listview控件进行了自定义设置，包括时事新闻，通讯录，RSS订阅  
b.RSS订阅功能中更新了可选择RSS源的功能（目前并不完善）

### 11.17重要更新
a..对我的模块中个人信息的更新进行了优化（防止二次进入造成app的闪退）  
b..对部分界面进行了微调（业务办理，时事新闻，民兴搜索）

### 11.18项目更新日志
a.对载入界面的界面进行了优化  
b.对搜索界面进行了优化

### 11.20项目更新日志
a.RSS订阅的选择订阅对象的功能暂时还没有解决问题，暂时搁置  
b.个人信息中加入了生成二维码的功能  
c.对载入界面进行了重新设计，参照百度云app

### 11.21项目更新日志
a.财务公告完成与后台的交互  
b.投票公告完成与后台的交互

### 11.22项目更新日志
1.写入信息到标签中时将信息上传到后台并存入数据库（并不完善）  
2.民意征集中  
需要解决的问题：  
a.参选人列表需要通过get方式从后台获取（将后台数据库中的信息传入spinner中）  
b.选取其中一位参选人通过post参选人姓名然后通过get方式可得到相关信息（职务，事迹，票数）  
c.点击票数则通过post方式更改票数  
d.点击围观则通过intent方式传递已经从后台获取的数据并显示在投票情况listview中

### 11.23项目开发日志
1.需要加返回的界面：我的中的所有界面（个人信息，传送个人信息，民意征集，投票情况，信息查询，写入信息，民兴通讯，民兴闲聊，RSS新闻，新闻详情，账号安全，帮助和反馈，关于民兴之家）  
2.目前项目存在的bug：  
a.个人信息的二次更新或以上会造成app闪退  
b.民兴搜索的结果展示第一次不会出现  
c.自动登录的限制条件不完善  
d.RSS订阅中选择订阅的新闻的功能不完善  
e.民兴社区出现了闪退  
3.目前项目尚未实现的功能：  
a.登录注册的验证  
b.农业资讯的获取和展示  
c.民意征集与后台的交互  
d.载入界面的图片自动更新  
e.业务办理界面的图标没有进行统一  
4.对大段textview中的文本进行了行距的设置  
5.对不支持NFC的设备在进入信息查询或信息传送功能时进行了提醒，不会直接造成报错

### 11.24项目开发日志
1.更改了民兴搜索中的搜索历史设置，取消了测试数据  
2.给需要加返回键的界面加上返回键  
3.身份证号码的限制&&两次密码的核对

### 11.25码主今日休息
最近在纠结三方的事情，因为钱，因为工作内容，其实落到实处也不过是心里没底，下周一是必须要拿的了，不能再纠结了。

### 11.26项目开发日志
1.更新登录界面  
2.更新载入界面  
3.更新RSS界面

### 11.27项目开发日志
1.新增了引导界面  
2.对提示用语进行了统一

### 11.28项目开发日志
1.引导界面设计以及图片的编辑  
2.制定本周的项目计划

### 11.29项目开发日志
1.解决了单个fragment的网路加载问题，但二次进入设置界面依然会出现闪退的问题  
2.民兴搜索列表中可以加入下拉刷新的功能（未实现）以及用户UGC的入口（已实现）

### 12.1项目开发日志
1.实现形式：第一次获取保存在数据库，第二次如果数据库有则从数据库中获取，没有则网络  
2.优化了网络的二次加载问题  
3.接下来的主要精力需要放在农业资讯和民意征集上

### 12.2项目开发日志
1.设置了二次加载的限制后，个人信息更新后并不能保存（仍然存在bug）  
2.更新了帮助与反馈界面，增加了留言板的功能  
3.搜索界面直接出现结果，可以直接清空搜索框  
4.更新了分享功能  
5.调整了搜索列表界面首次无法加载的情况

### 12.5项目开发日志
1.收集当前存在的一些问题  
离线状态下：  
a.民兴搜索出现问题  
b.个人信息更新出现问题  
c.一些提示不正确（未登录依然显示登录成功之类的问题）  
在线状态下：  
a.二次加载造成资源浪费的问题  
b.使用Intent传送资源时再返回会自动清除  
2.集中在农业助手，民意征集  
a.农业助手  
Android：通过标签来获取数据库资源→点击类别进行网络加载→下拉可以刷新以及定时自动刷新  
后台：通过网络爬取数据存入数据库  
b.民意征集

### 12.6项目开发日志
1.民意征集实现了与后台的交互  
2.民意征集仍然存在的问题：  
a.投票的限制设置  
b.投票后的数据更新导致app出现问题  
3.农业资讯的数据库设置，后台已完成  
类别（热点，本地，农业新闻，农业政策，生产指导，其他），标题，来源，时间，图片，内容  
4.反馈的功能（针对什么内容的反馈是什么？）  
a.使用反馈前后台已完成  
b.搜索反馈前后台功能已完成

### 12.7项目开发日志
1.实现农业资讯的功能  
2.fragment向activity传递存在问题
