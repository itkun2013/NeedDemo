# NeedDemo
一级标题请使用#，平时敲的demo.需要的可以下载看看 
(---------------代表上面的文字是二级文字大小)

## 我是二级标题
学习markdown,直接回车不能换行（使用`<br>`），<br>
我换行了，哈哈哈
### 文字要高亮
tab键上面的、号,英文书写
Thank `you`. Please `call` Me `code`
#### 文字超链接
给一段文字加入超链接的格式是这样的 [ 要显示的文字 ]( 链接的地址 )。
比如 [我的github地址](https://github.com/itkun2013)
#### 插入符号，圆点符
编辑的时候使用的是星号 *
* 隔壁老王
* 隔壁金兔子
* 隔壁老宋
此外还有二级圆点和三级圆点，就是多加一个Tab
* 隔壁老王
  * 隔壁金兔子
    * 隔壁老宋

##### 缩进
>数据结构  
>>树  
>>>二叉树  
>>>>平衡二叉树  
>>>>>满二叉树  
##### 插入网络图片
叹号! + 方括号[ ] + 括号( ) 其中叹号里是图片的URL。
![baidu](http://www.baidu.com/img/bdlogo.gif) 
##### GitHub仓库里的图片
https://github.com/ 你的用户名 / 你的项目名 / raw / 分支名 / 存放图片的文件夹 / 该文件夹下的图片<br>
![](https://github.com/itkun2013/NeedDemo/raw/master/refresh/src/main/res/mipmap-xhdpi/a2a.png)  
##### 插入代码片段
在代码的上一行和下一行用` `` 标记。``` 不是三个单引号，而是数字1左边，Tab键上面的键。<br>
要实现语法高亮那么只要在 ``` 之后加上你的编程语言即可（忽略大小写）
``` java
private void loadData() {
        if (mDatas != null && mDatas.size()> 0) {
            showContentView();
            setAdapter(mDatas);
        } else {
            loadAndroidData();
        }
    }
```
###mardkdown学习完毕
