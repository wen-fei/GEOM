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
     # 匹配规则1：标准的<p>Abstarct</p><p>...</p>
     patterns = re.compile(r'\D+<p>(.*?)</p><p>(.*?)</p>')
     match = patterns.match(content)
     if match:
          return match.group(2)
     else:
          return "null"

test_str = """
</p>
<p>Abstract
</p>
<p>Background: Medical concepts are inherently ambiguous and error-prone due to human fallibility, which makes it hard for
them to be fully used by classical machine learning methods (eg, for tasks like early stage disease prediction).
Objective: Our work was to create a new machine-friendly representation that resembles the semantics of medical concepts.
We then developed a sequential predictive model for medical events based on this new representation.
Methods: We developed novel contextual embedding techniques to combine different medical events (eg, diagnoses, prescriptions,
and labs tests). Each medical event is converted into a numerical vector that resembles its “semantics,” via which the similarity
between medical events can be easily measured. We developed simple and effective predictive models based on these vectors to
predict novel diagnoses.
Results: We evaluated our sequential prediction model (and standard learning methods) in estimating the risk of potential
diseases based on our contextual embedding representation. Our model achieved an area under the receiver operating characteristic
(ROC) curve (AUC) of 0.79 on chronic systolic heart failure and an average AUC of 0.67 (over the 80 most common diagnoses)
using the Medical Information Mart for Intensive Care III (MIMIC-III) dataset.
Conclusions: We propose a general early prognosis predictor for 80 different diagnoses. Our method computes numeric
representation for each medical event to uncover the potential meaning of those events. Our results demonstrate the efficiency
of the proposed method, which will benefit patients and physicians by offering more accurate diagnosis.
</p>

"""
print(get_abstrct_by_re(test_str))
# FILE_PATH = "../data/test1.pdf"
# content, _ = get_pdf_content(fileName=FILE_PATH)
# print(content)
