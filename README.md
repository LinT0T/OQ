# OQ
## OQ
---
重要：GitHub上app\release中APP为最新版，其他版本有bug
### 主要功能
---
#### 登录界面
 启动OQ
 
![启动](http://q59jhs5xi.bkt.clouddn.com/qidong.jpg)
 
登陆界面

![登录界面](http://q59jhs5xi.bkt.clouddn.com/main.jpg)
   
 可以进行注册，找回密码操作
   
 ![注册界面]( http://q59jhs5xi.bkt.clouddn.com/zhuce.jpg)
   
 ![救援界面](http://q59jhs5xi.bkt.clouddn.com/152434.jpg)
 
 ---
 #### 主界面
 ![主界面](http://q59jhs5xi.bkt.clouddn.com/152600.jpg)
   
   右上角点击会出现菜单。
   
     
   点击可以进入聊天，聊天内容由图灵机器人实现。点击左上角头像可以进入设置页面，可以设置头像（头像图片会上传到七牛云），退出当前账号，删除当前账号
     
![菜单界面](http://q59jhs5xi.bkt.clouddn.com/aaa.jpg)
  
  
![聊天界面](http://q59jhs5xi.bkt.clouddn.com/hello.jpg)
     
   点击下方第二个按钮可以切换到联系人
     
 ![联系人界面](http://q59jhs5xi.bkt.clouddn.com/friends.jpg)
 
 此处点击MIKU可以设置MIKU头像，清除聊天记录
 
 点击下方第三个按钮可以切换到动态
 
 ![主界面](http://q59jhs5xi.bkt.clouddn.com/qzone.jpg)
 
 点击第一个按钮进入逼乎
 ---
 #### 逼乎
 登录后进入逼乎
 
  ![登录界面](http://q59jhs5xi.bkt.clouddn.com/逼乎开始.jpg)
 
 ![主界面](http://q59jhs5xi.bkt.clouddn.com/bihujiemian.jpg)
 
 可以发布问题，修改密码
 
  ![登录界面](http://q59jhs5xi.bkt.clouddn.com/bbb.jpg)
  
   ![登录界面](http://q59jhs5xi.bkt.clouddn.com/bihuaaa.jpg)
   
---
### 主要技术
- 图灵机器人

参考官方文档实现聊天
- 七牛云
  
参考官方文档实现了上传图片
- Recyclerview
  
在主界面，聊天界面，联系人，逼乎中使用到recyclerview
- 约束布局

逼乎中recyclerview的item使用了约束布局
- Fragment

OQ主界面由3个Fragment实现

- 自定义imageview

参考https://www.jianshu.com/p/6beca370fd50 实现了圆形imageview
- 动画

通过查资料实现了主界面右上角菜单的特殊显示和隐藏，实现了逼乎界面下方菜单栏的特殊显示和隐藏
- 存储

使用了sharepreference保存用户名，密码，头像图片，聊天记录，实现记住密码和自动登录，同时使用了七牛云保存头像图片
- 网络请求及json解析

在OQ中全部使用Android原生网络请求和解析，在逼乎中出于测试使用了okhttp3进行网络请求，使用Android原生解析

---
### 心得体会
- 这次寒假考核写了十多天，让我对Android开发从略显生疏到了较为熟悉，曾经头疼不已的recyclerview的适配器也可以熟练掌握了，其他一些不熟悉的控件通过查询资料等方式也成功使用，对网络请求和json解析更进一步，我认为是对我的一次全方位的提升。


### 主要碰到的问题
- 一开始是recyclerview的使用，然后是图灵机器人的接入，获取图片的uri，还有过多使用sharepreference导致APP经常卡顿，复杂JSON解析的问题。

---
## 在此期间，我多次碰到直接不会的地方和难以解决的bug，感谢帮助过我的学长们，谢谢你们充满耐心，不嫌麻烦的回答我的问题，没有你们也没有这个app，非常非常感谢学长们！
