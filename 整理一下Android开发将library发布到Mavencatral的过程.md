### 整理一下Android开发将library发布到Mavencatral的过程

## 前提条件是  library已经开发完毕

  示例:

- 我的开发环境

  ![image-20230521162937067](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230521162937067.png)

- gradle的版本是7.4

  ![image-20230521163146538](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230521163146538.png)

- 开发的library的project为: com.example.permissonx

- 对应的library的包名为:com.permissionx.kunkun

  

## 上传到Mavencentral

###      账号的注册: 

地址: https://issues.sonatype.org/secure/Dashboard.jspa

![注册示例](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230519171102277.png)

点击后进行进入注册页面: 

![image-20230522074936599](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522074936599.png)

注册登录后记得点击登录,登录完成会提醒你进行语言的切换

![image-20230522075043984](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522075043984.png)

### 新建一个问题:

![image-20230522075258049](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522075258049.png)

填写一些信息: 例如当前我的学习的仓库地址为: https://github.com/NYK1024212458/PermissonX

![image-20230522105702723](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522105702723.png)

> 一些注册时候填写的说明:
>
> - 项目->  Community Support - Open Source Project Repository Hosting (OSSRH)  
>
> - 问题类型->New Project 
>
> - 概要: 填写项目名:PermissonX
>
> - 描述:可以是自己项目的一些说明
>
> - 附件->不管
>
> - Group Id: 最新的规定,使用io.github.github的用户名;  如果有自己的域名,需要阅读下面的链接, https://central.sonatype.org/publish/    
>
>   Group Id官方的说明:https://central.sonatype.org/publish/requirements/coordinates/
>
> - project url -> 填写我们的github地址   https://github.com/NYK1024212458/PermissonX
> - SCM url->  填写我们clone时候的git地址:  https://github.com/NYK1024212458/PermissonX.git
>
> - UserName(s)->可以操作部署该项目的用户  -> 这里我填写的是 sonatype的用户名;
>
> - Already Synced to Centra:  是否已经同步到中央仓库  ->  我们暂时没有
>
>   

点击新建,完成新建

#### 新建完成后整体的展示:

![image-20230522160335446](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522160335446.png)

### 与经办人进行交互

等待一会儿就又有经办人留言,接下来的操作:

![image-20230522161037258](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522161037258.png)

> 1. 问题一需要新建一个空的仓库
> 2.  需要修改我们的Group Id ,因为我第一次使用的是com.gihub......,新规定需要io.gihub.......
> 3. 修改完成后记得将status打开

<img src="https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522170631874.png" alt="image-20230522170631874" style="zoom:33%;" />

等待处理;很快就会处理完毕,基本上按照上面填写不会出错.

![image-20230520171041705](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230520171041705.png)

> 翻译如下:恭喜你!欢迎来到中央存储库!
> io.github.nyk1024212458已经准备好了，现在用户kunkun5love.com可以:
> 将快照和释放工件发布到s01.oss.sonatype.org
> 看看我们官方指南的这一部分，了解部署说明:
> https://central.sonatype.org/publish/发布-指南/# 部署
>
> 根据您的构建配置，您的第一个组件可能会在成功部署后自动释放。
> 如果发生这种情况，您将在此票证上看到一条评论，确认您的工件已同步到Maven Central。
> 如果您在一两个小时内没有看到此评论，则可以按照指南本节中的步骤进行操作:
> https://central.sonatype.org/publish/release/

### 准备GPG

​    官网:https://gpgtools.org/

#### 安装GPG

- 官方下载安装  :  https://gpgtools.org/   如果你是win的话参考如下链接: https://blog.csdn.net/u011174139/article/details/120139497
- 使用  homebrew安装       `brew install gpg`

我使用homebrew安装,各种问题,最后还是选择了直接下载安装.

<img src="https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522172145623.png" alt="image-20230522172145623" style="zoom: 25%;" />

这边如果直接打开新建,最后格式是asc格式,虽然我多次导出时候修改为gpg格式,多次都出现了最后pushing时候出错.

##### 这边推荐也是我最后ok的过程:  命令行生成秘钥,使用GPG软件将公钥上传到服务器进行公开,操作如下

1. 安装完毕后使用命令行生成秘钥:   gpg --full-gen-key 

    密钥类型选择:RSA和RSA(默认)

   密钥的长度:4096

   秘钥有效期:0或者按照自己需要来

   最后生成的pua rsa 的后八位我们需要保存一下,这个就是我们在后续配置的signing.keyId=后八位数字

     

   > > niuyukun@niuyukundeMBP ~ % gpg --full-gen-key
   > >
   > > gpg (GnuPG/MacGPG2) 2.2.41; Copyright (C) 2022 g10 Code GmbH
   > > This is free software: you are free to change and redistribute it.
   > > There is NO WARRANTY, to the extent permitted by law.
   > >
   > > 请选择您要使用的密钥类型：
   > >    (1) RSA 和 RSA （默认）
   > >    (2) DSA 和 Elgamal
   > >    (3) DSA（仅用于签名）
   > >    (4) RSA（仅用于签名）
   > >  （14）卡中现有密钥
   > > 您的选择是？ 1
   > > RSA 密钥的长度应在 1024 位与 4096 位之间。
   > > 您想要使用的密钥长度？(3072) 4096
   > > 请求的密钥长度是 4096 位
   > > 请设定这个密钥的有效期限。
   > >          0 = 密钥永不过期
   > >       <n>  = 密钥在 n 天后过期
   > >       <n>w = 密钥在 n 周后过期
   > >       <n>m = 密钥在 n 月后过期
   > >       <n>y = 密钥在 n 年后过期
   > > 密钥的有效期限是？(0)
   > > 密钥永远不会过期
   > > 这些内容正确吗？ (y/N) y
   > >
   > > GnuPG 需要构建用户标识以辨认您的密钥。
   > >
   > > 真实姓名： niuyukun
   > > 电子邮件地址： kunkun5love@gmail.com
   > > 注释：
   > > 您选定了此用户标识：
   > >     “niuyukun <kunkun5love@gmail.com>”
   > >
   > > 更改姓名（N）、注释（C）、电子邮件地址（E）或确定（O）/退出（Q）？ O
   > > 我们需要生成大量的随机字节。在质数生成期间做些其他操作（敲打键盘
   > > 、移动鼠标、读写硬盘之类的）将会是一个不错的主意；这会让随机数
   > > 发生器有更好的机会获得足够的熵。
   > > 我们需要生成大量的随机字节。在质数生成期间做些其他操作（敲打键盘
   > > 、移动鼠标、读写硬盘之类的）将会是一个不错的主意；这会让随机数
   > > 发生器有更好的机会获得足够的熵。
   > > gpg: 吊销证书已被存储为‘/Users/niuyukun/.gnupg/openpgp-revocs.d/7331155139B9D8E513BA6633CD7AF840C050E0F4.rev’
   > > 公钥和私钥已经生成并被签名。
   > >
   > > pub   rsa4096 2023-05-21 [SC]
   > >       7331155139B9D8E513BA6633CD7AF840后八位这里就以文字替代了哈
   > > uid                      niuyukun <kunkun5love@gmail.com>
   > > sub   rsa4096 2023-05-21 [E]
   > >
   > > niuyukun@niuyukundeMBP ~ %
   > >
   > > 

2. 导出GPG文件    `gpg --export-secret-keys -o secring.gpg` 

   会弹出一个输入框输入刚才的密码:

   ![image-20230522173503088](/Users/niuyukun/Library/Application Support/typora-user-images/image-20230522173503088.png)

   在项目的根目录就会生成gpg文件:

   ![image-20230522173741081](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522173741081.png)

3. 打开GPG keychain 上传公钥到服务器

   打开软件,会出现我们使用命令行创建的秘钥对,也会出现上传到秘钥的提示,具体参考我上面使用软件时候的截图

   <img src="https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522173823550.png" alt="image-20230522173823550" style="zoom:50%;" />

   点击上传: 

   <img src="https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522173853044.png" alt="image-20230522173853044" style="zoom:50%;" />

   

   ### **使用插件上传到mavencentral**

    查阅了很多的文章,大多数都是自己创建一个gradle,然后写一堆东西,看的人头大,最后找到可以使用第三方插件进行上传,配置的东西很少; 

      插件地址:https://github.com/vanniktech/gradle-maven-publish-plugin

   #### 依赖第三方的上传插件: project下的build.gradle

   ```
   //  使用插件上传
   id "com.vanniktech.maven.publish" version "0.25.2"
   ```

   ![image-20230522174426688](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522174426688.png)

   #### 在需要上传的librar下的buil.gradle使用此插件

   ```kotlin
   // 使用插件
   id 'com.vanniktech.maven.publish'
   ```

   ![image-20230522174601568](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522174601568.png)

   

   #### 重新编译后就会有publishing的task:

   <img src="https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522174925801.png" alt="image-20230522174925801" style="zoom:50%;" />

   > 如果没有task,按照我如下的操作
   >
   > 1. AS设置里面进行设置: Experimental -> Gradle   将 Do not build Gradle task list during Gradle sync  去掉勾选.
   >
   >    <img src="https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522175615074.png" alt="image-20230522175615074" style="zoom:25%;" />
   >
   > 2. 然后进行重新编译:   File-> Sync Project wirh Gradle Files
   >
   > 3. ![image-20230522175710379](/Users/niuyukun/Library/Application Support/typora-user-images/image-20230522175710379.png)
   >
   >    运行完毕后再点击gradle ,就会有task任务了

### 配置第三方插件需要的一些配置信息: 再project下的gradle.properties下添加如下信息

  可以参考第三方的文档:https://vanniktech.github.io/gradle-maven-publish-plugin/central/#configuring-the-pom

我全部整理到一个里面了:

```kotlin
# 插件上传的mavencentral的配置开始
#SONATYPE_HOST 默认不修改,直接复制
SONATYPE_HOST=DEFAULT
# or when publishing to https://s01.oss.sonatype.org
#SONATYPE_HOST默认不修改
SONATYPE_HOST=S01
#RELEASE_SIGNING_ENABLED 默认不修改
RELEASE_SIGNING_ENABLED=true
#GROUP也就是我们新建问题时候的GroupId
GROUP=io.github.NYK1024212458
#也可以根据自己需要写
POM_ARTIFACT_ID=Project.getName()
VERSION_NAME=Project.getVersion()
#可以根据自己实际填写
POM_NAME=library
POM_DESCRIPTION=A description of what my library does.
#inception  
POM_INCEPTION_YEAR=2020
# 填写我们对应的github的仓库地址
POM_URL=https://github.com/NYK1024212458/PermissonX/
#下面的默认就行
POM_LICENSE_NAME=The Apache Software License, Version 2.0
POM_LICENSE_URL=https://www.apache.org/licenses/LICENSE-2.0.txt
POM_LICENSE_DIST=repo
#默认的结束
#对应的仓库地址
POM_SCM_URL=https://github.com/NYK1024212458/PermissonX/
#对应的仓库的git的地址,可根据自己进行修改
POM_SCM_CONNECTION=scm:git:git://github.com/NYK1024212458/PermissonX.git
POM_SCM_DEV_CONNECTION=scm:git:ssh://git@github.com/NYK1024212458/PermissonX.git
#开发者信息
POM_DEVELOPER_ID=kunkun5loveblog
POM_DEVELOPER_NAME=niu yukun
POM_DEVELOPER_URL=https://github.com/NYK1024212458/
#注册mavenCentral的账号和密码
mavenCentralUsername=账户
mavenCentralPassword=密码


signing.keyId=再gpg说的后八位,也就是生成gpg的指纹
signing.password=生成GpG时候我们自己输入的密码
signing.secretKeyRingFile=/Users/niuyukun/secring.gpg  #生成的gpg的位置
# 插件上传的mavencentral的配置结束
```

> > **特别注意：私有信息不要提交到 git 版本管理中，可以写在 `local.properties` 中，等到要发布组件时再复制到 `gradle.properties` 中。而私钥文件也不要保存在当前工程的目录里，可以统一放到工程外的一个目录。**

### 进行上传

打开AndroidStudio的Terminal输入命令行进行上传:  `./gradlew publishAllPublicationsToMavenCentral`

![image-20230522183120512](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522183120512.png)

运行完毕后的展示:

![image-20230522183220455](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522183220455.png)

### 查看上传后仓库信息:

仓库地址更新了;现在使用的是新的地址:https://s01.oss.sonatype.org/  使用https://oss.sonatype.org注册时候的账号和地址

> ### 登录: https://oss.sonatype.org/   使用在https://oss.sonatype.org/相同的账号和密码
>
> Incorrect username, password or no permission to use the Nexus User Interface.
> Try again.Forbidden
>
> 如果登录老的地址https://oss.sonatype.org/  会出现上面相同的问题,使用新地址

![image-20230522183444652](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522183444652.png)

此时我们看到是在临时仓库的library,还需要进行发布

### 点击Release发布:

### ![image-20230522183626538](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522183626538.png)

点击confirm确定进行发布

### 发布后进行查看:  https://central.sonatype.com/

![image-20230522183818954](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522183818954.png)

输入我们上传时候的pom_name;我们写的是library,进行search

![image-20230522184212527](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522184212527.png)



点击可以查看具体的信息:

![image-20230522184329012](https://kunblogpicture.oss-cn-chengdu.aliyuncs.com/blogimg/image-20230522184329012.png)

主要参考的博文: https://mp.weixin.qq.com/s/wxFxIKcugIMhGxBAdlIbsg