#!/usr/bin/env python  
# -*- coding: utf-8 -*-

"""
@author: TenYun  
@contact: qq282699766@gmail.com  
@time: 2018/4/20 16:13 
"""
import re
import os
import tabula
abs_dir = os.path.dirname(__file__)
# path = os.path.join(abs_dir, "../data/26295473.txt")
path = os.path.join(abs_dir, "../data/26628673.txt")

table_start_index = []
with open(path, 'r+', encoding='utf-8') as file_data:
    lines = file_data.read().split("\n")
    # for index, line in enumerate(lines):
    #
    #    规则一
    #    规则1.1：表格如26295473.pdf、26628673.pdf等文件中得形式，每个表格标题都是Table + 编号 + title，也有的title可能换行
    #    这样可以定位表头
    #    规则1.2：加入Table后面跟得不是title，则title一般在下面一到两行内。如果不是title，而是表格得字段名称，那么一般每个字段
    #    之间会有大量空格隔开
    #    规则二
    #    规则2.1：定位表中数据：表中数据大部分为数字，可以将每一行去除空格，然后变为纯字符，统计每一行中得数字个数，求平均值
    #    当某一行中的数字突然急剧下降得时候，说明表格结束
        # line = line.lstrip()
        # if line.startswith("Table"):
        #     table_start_index.append(index)
        # print(str(index) + "---->" + line)
    table_list = lines[166:198]
    for s in table_list:
        # 去除两端空格
        s = s.strip()
        # 去除linux系统下得滴滴
        s = s.replace("\x07", "")
        # 解决无规则空格分隔问题
        s = re.split(r" {3,}", s)
        if len(s) < 2:
            continue
        print(s)

    # print(table_list)
# print(table_start_index)



