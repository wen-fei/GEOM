# GEOM
Building Knowledge Graph based PICO  for ERM



### 一、 安装和配置

1.  robotreviewer3使用python3.5，直接执行github给出的安装脚本会报错

2. 按照GitHub给出的yml文件创建conda虚拟环境时，个别包由于一些原因无法直接安装

   主要有三个包：- ptyprocess=0.5.1 - spacy=0.101.0 （conda） - python-Levenshtein==0.12.0（pip） 解决方法conda换pip安装 python-Levenshtein

   换成whl文件安装方式