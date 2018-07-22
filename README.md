# GEOM
Building Knowledge Graph based PICO  for ERM



### 一、 安装和配置

1.  robotreviewer3使用python3.5，直接执行github给出的安装脚本会报错

2.  按照GitHub给出的yml文件创建conda虚拟环境时，个别包由于一些原因无法直接安装

主要有三个包：- ptyprocess=0.5.1 - spacy=0.101.0 （conda） - python-Levenshtein==0.12.0（pip） 解决方法conda换pip安装 python-Levenshtein换成whl文件安装方式


### 二、 遇到的一些问题

1. 启动Grobid的时候Python FileNotFoundError: [WinError 2] 系统找不到指定的文件。

   解决办法：http://blog.csdn.net/zhangda0000001/article/details/73608847

   在windows 在子进程中使用echo，需要设置 shell =True，因为 echo 不是单独的命令，而是window CMD 内置的命令。并且，需要注意，只有在绝对需要的情况下才使用shell=True

   找到 subprocess.py脚本，将对应行加上shell=true 参数。该问题解决。
## 新论文
自动抽取pico
使用框架：tensorflow
