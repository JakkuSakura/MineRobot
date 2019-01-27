# MineRobot
这是一款mod，最初被设计为协助玩家挖矿的自动软件，现在是功能极其强大的一套框架。可以在纯净服务器上使用。

## 支持以下功能：
- [x] 编写Python脚本，可以使用全部的forgeAPI和大部分Python库，高度自定义
- [x] 批量挖掘方块
- [x] 放置方块
- [x] 杀人成佛模式
- [x] 自动寻路
- [x] 自动挖矿
- [x] 拿木棒可以记录方块
## 使用方法
本MOD没有对游戏本身做任何改动，只有一个命令`/robot`
### /robot start method
执行在script目录下的method.py脚本
附带的有：
automine -- 自动挖掘，用当前工具，从当前位置开始挖掘鱼骨矿道，有3个参数可以使用，分别代表分矿道间隔-1、分矿道长度和矿道条数。
destroy -- 挖掘记录下的块或指定坐标(x y z)的块，需要接近目标。
killer -- 每300ms，对周围所有生物攻击一次。间隔可以指定
put -- 在指定位置放置指定物品
walkto -- 寻路到某位置或记录的位置，接受可选参数x y z
### /robot stop
停止脚本的执行，清空方块记录器
### /robot reload
在某些情况下，需要清空缓存，用这个命令。将会重新加载Jython解析器
### /robot show
查看记录下的方块位置，当前物品的名称和当前亮度。（调试用）

## 关于脚本系统
脚本系统为jython 2.70，语法同python 2.70。本身提供了一些示例脚本，可以参考实现。
规则是：scripts/filename.py的主函数为filename
```python
def filename(arg1, arg2=DefaultValue):
    #your own code
```