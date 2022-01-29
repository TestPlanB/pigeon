# pigeon

## pigeon 是一个完全由kotlin编写的适用于android开发的数据总线型库，便于在项目中实现数据解耦，实现方便且稳定的数据流通讯
与市面上常见的总线型库相比，如eventbus ，有如下特点：
* **无需初始化，随时随地想使用就使用**
* **无需进行注册/解除注册操作，如eventbus的register操作，实现订阅/发送 一行代码即可，无需担心由消息引起的内存泄露**
* **轻量级，可直接实现源码级别接入**
* **支持订阅线程切换，线程切换依靠协程，轻量，简单实现订阅自由**
* **底层实现采用flow api，稳定，更加高效**
* **流程通俗易懂，便于学习和二次开发**

## 原理解析
https://juejin.cn/post/7058603761693884446/

## 使用说明
### 发送数据
```
实现lifecylceOwner的类可以采用如下方式
this.post(数据，【可选，是否是粘性事件，默认为false】)

或者直接使用库提供的方式
MessageCenter.post(event,isStick)
```

### 接收/订阅数据

```
this.subscribeEvent(数据类型，如String::class.java,【可选消息处理线程，默认是主线程】) {
               dosomething
            }
```
## 项目层级介绍
* **包含module分别为测试代码与核心代码**
* **常规的代码层级**

## 环境准备
建议直接用最新的稳定版本Android Studio打开工程。目前项目已适配`Android Studio Arctic Fox | 2020.3.1`，
低版本的Android Studio可能因为Gradle版本过高而无法正常打开项目。
