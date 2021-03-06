# 系统简介

最近有个需求，需要罗列出各个目录中文件的信息，检索各类文件的最新版本。网上看了很多方式，但发现没有合适的。于是利用空余时间开始编写了一套文件遍历系统，如此便有了Laver(紫菜)。Laver遵从简单好用的原则，用户友好的UI界面。且所有代码基于Java，每一个自定义的类与方法都有详尽的纯中文注释信息，方便二次开发。

Laver，最初起自Lastest Version，发现两者结合后正好是我喜欢的蔬菜之一。缘，妙不可言。

# 功能演示

![功能演示](http://zone.heicaijun.cn/markdown/function.gif)

# 功能说明

- 遍历目录：可以对选中的根目录进行深度遍历，检索出所有的文件及其父目录与完整路径信息。
- 格式限定：支持对需要检索的文件格式进行限定，内置常用的文档格式(WPS,PDF等)，且支持用户自定义格式。
- 检索深度：支持对深度进行限定，当检索深度为1时，可检索根目录下一层目录的所有文件
- 检索模式：支持对检索方式进行定义，系统自带匹配文件名中带有ver，_v，Ver等版本标识符的字符串，对其后面的版本号进行分析，只列出最新版本的文件。或者用户自定义的其他类型字符串。
- 检索结果可排序：对检索的结果可以根据字典顺序迅速找到你需要查找的文件，适用于文件找不到的情况。
- 检索结果可导出：检索到的信息可以生成Excel，方便文档归档与后续分类

# 快速开始

你可以通过git下载可执行的exe文件。下载链接为[https://github.com/heicaijun/Laver/releases](https://github.com/heicaijun/Laver/releases)，让我们通过简单的学习开始使用吧！

## 基础功能使用

1. 首先设定需要扫描的根目录，选择你需要扫描的文件夹。

![image-20200926183440127](http://zone.heicaijun.cn/markdown/image-20200926183440127.png)

2. 选定你需要扫描的文件类型，勾选all选项以列出所有类型文件。也可以用系统筛选出wps，pdf等常用的文件类型，你甚至可以选择other选项自定义要扫描的格式

![image-20200926184910495](http://zone.heicaijun.cn/markdown/image-20200926184910495.png)

3. 设定需要扫描的深度，0代表无限层(由于其底层算法为递归，所以请避免检索文件过多的根目录，或通过扫描格式与扫描深度来限制，以免程式卡死)，输入1-20可以扫描根目录下的1-20级子目录

![image-20200926185220910](http://zone.heicaijun.cn/markdown/image-20200926185220910.png)

4. 点击开始扫描即可运作，扫描到的结果将会列入到下方的表格中。

![image-20200926185324045](http://zone.heicaijun.cn/markdown/image-20200926185324045.png)

5. 点击导出结果至Excel按钮可以将扫描到的内容保存到Excel中，方便后续使用Excel工具来处理扫描数据。

![image-20200926185505261](http://zone.heicaijun.cn/markdown/image-20200926185505261.png)

## 高级功能使用

> 如您对如下表述存在疑问，则不建议使用这些功能。您可以通过留言提出疑问

1. 扫描的格式可以是用户自定义的格式，方便用户列出自己需要检索但系统未自带的特殊格式文件，用户可以在其他格式框内输入用逗号隔开的多个文件后缀名，比如用户可以在框内输入"cad,psd,png,gif"来检索出所有需要的图片。

![image-20200926192748249](http://zone.heicaijun.cn/markdown/image-20200926192748249.png)

2. 扫描的模式可以选择三种不同的模式

| 扫描模式     | 描述                                                         |
| ------------ | ------------------------------------------------------------ |
| 全扫描       | 即无论是什么版本的文件都会将之全部扫描出来                   |
| 最新版本模式 | 系统自带的规则，可以根据文件名中的[version,ver,v]这三种版本标识符来整理出版本号，并过滤掉旧版本 |
| 自定义规则   | 你可以在输入框中输入用英文逗号隔开的版本标识，诸如[ 版本,文档,spec ]。 |

需要注意的是，版本标识符特指文件名中可以明显区分版本号的字符，比如文件*Sample File Ver_1.0.1.xlsx*标本标识符即为ver，不难发现自ver到文件末尾均为数字与连接字符。所以版本标识符到文件末尾**不允许**出现除数字与通用的连接符（诸如：_ ( ) [ ] - （ ） 等）外的**其他任何字符**。否则系统将无法判断文件是否为同一文件的不同版本。

![image-20200926193528328](http://zone.heicaijun.cn/markdown/image-20200926193528328.png)

# 开发指南

如果你想要学习、开发、修改或自行构建Laver，可以依照下面的指示：

1. 掌握基础的java知识(尤其是java.util.IO类)
2. 掌握Swing开发，熟悉WindowBuilder插件的使用
3. 熟悉git版本管理工具的使用，至少会pull操作。

## 环境搭建

### 基础开发环境

jdk版本至少为1.7，开发工具建议Eclipse，网上教程很多，这里不赘述。如选择IDEA请对应安装JFormDesigner插件(收费插件)

### WindowBuilder插件安装

> 考虑到国内用户下载国外源的时候会报错，所以建议使用国内源下载

1. 进入 WindowBuilder 的官网 ： https://www.eclipse.org/windowbuilder/download.php。
2. 选择 1.9.3 的 link ， **右键点击，复制连接地址：**http://download.eclipse.org/windowbuilder/1.9.3/ ， 这个是官方提供的下载源。

![image-20200924215035297](http://zone.heicaijun.cn/markdown/image-20200924215035297.png)

3. 将官方下载源修改为国内的：

| http://download.eclipse.org/windowbuilder/1.9.3/      |
| --------------------------------------------------------- |
| **http://mirror.bit.edu.cn/eclipse/windowbuilder/1.9.3/** |

4. 依次点开Help ->Install New Software

![image-20200924215705345](http://zone.heicaijun.cn/markdown/image-20200924215705345.png)

5. 点击Add...按钮 ，将国内源：http://mirror.bit.edu.cn/eclipse/windowbuilder/1.9.3/ 复制到Location框中，Name可以随便起，这里起WindowBuilder，然后点击Add

![image-20200924220136245](http://zone.heicaijun.cn/markdown/image-20200924220136245.png)

6. 点击 Select All ， 点击 *\*next\** 开始下载。路上一路狂点Next即可

![image-20200924220356899](http://zone.heicaijun.cn/markdown/image-20200924220356899.png)

7. 最后点击I Accept 表示接受，并点击Finish即可等待安装完成。

![image-20200924220827531](http://zone.heicaijun.cn/markdown/image-20200924220827531.png)
