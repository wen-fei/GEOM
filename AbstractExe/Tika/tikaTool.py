#!/usr/bin/env python  
# -*- coding: utf-8 -*-

"""
@author: TenYun  
@contact: qq282699766@gmail.com  
@time: 2018/4/8 20:03 
"""

############### tika #####################
import tika
tika.initVM()
from tika import parser
##########################################
import re
import os

def get_pdf_content(fileName):
    """
     抽取文献内容, 返回pdf内容和元数据
     :param fileName:
     :return:
     """
    parsed = parser.from_file(fileName, xmlContent=True)
    content = parsed["content"]
    metadata = parsed["metadata"]
    return content, metadata


def get_abstrct_by_re(content):
     """
     使用正则匹配从数据中抽取abstract
     :param content:
     :return:
     """
     # pattern = r"<p>[Aa]bstract(\s)</p>(\s)<p>(.*?)</p>)"
     # 去除字符串中的换行符
     content = content.replace("\r\n", "")
     content = content.replace("\n", "")
     content = content.lower()
     # 匹配规则1：标准的<p>Abstarct</p><p>...</p>
     pattern1 = re.compile(r'\D+<p>[Aa]bstract</p><p>(.*?)</p>')
     match1 = pattern1.match(content)
     if match1:
          return match1.group(1)
     else:
          pattern2 = re.compile(r'.*<p>abstract(.*?)introduction')
          match2 = pattern2.match(content)
          if match2:
              return match2.group(1)
          else:
              return None



file_path = "D:\\论文库\\知识图谱\\数据集\\2016 4-9"
listfiles = os.listdir(file_path)
errorFiles = []
for file in listfiles:
    # 判断文件类型
    file_ex = file.split(".")[1]
    if file_ex.lower() != 'pdf':
        errorFiles.append(file)
        print(file + '--------------------文件类型错误--------------->')
    else:
        content = get_pdf_content(os.path.join(file_path, file))
        if content[0] is None:
            errorFiles.append(file)
            print(file + '--------------------解析失败--------------->')
        else:
            abs_res = get_abstrct_by_re(content[0])
            if abs_res is None:
                errorFiles.append(file)
                print(file + '--------------------解析失败--------------->')
            else:
                print("得到Abstract======================>" + abs_res)
                print(file + '--------------------解析完成--------------->')
acc = 1- len(errorFiles) / len(listfiles)
print("提取准确率为：", acc)
# FILE_PATH = "../data/test1.pdf"
# content, _ = get_pdf_content(fileName=FILE_PATH)
# print(content)
