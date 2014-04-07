#! /usr/bin/env python
# -*- coding: utf-8 -*-
# vim:fenc=utf-8
#
# Copyright © 2014 ender xu <xuender@gmail.com>
#
# Distributed under terms of the MIT license.

"""
抓取爱情测试
"""
import sys
reload(sys)
sys.setdefaultencoding('UTF-8')
import logging
from pyquery import PyQuery as pq
from path import path
import json

logging.basicConfig(format='%(levelname)s:%(message)s', level=logging.DEBUG)
log = logging.getLogger('抓取爱情测试')

class Test(object):
    '测试'
    def __init__(self, filename, num):
        d = pq(filename=filename)
        self.num = num
        self.conclusion(d('.answer_list'))
        self.question(d('.option_list'))
        self.title = d('h1')[0].text
        self.type = 'jump'
        t = d('.inner_con_art')[0]
        self.content = t.text_content().split(u'本期')[0]
        if self.title.find(u'爱情') >= 0 or self.content.find('u爱情'):
            self.tags = [u'爱情']
        if self.title.find(u'爱情测试：') >= 0:
            self.title = self.title.replace(u'爱情测试：', '')

    def question(self, d):
        '问题'
        self.questions = []
        for t in d:
            q = {}
            q['num'] = int(t.attrib['id'].split('_')[1])
            q['title'] = t.find('dt').text
            q['answers'] = self.answer(pq(t)('li'))
            self.questions.append(q)

    def answer(self, d):
        '备选答案'
        ret = []
        for t in d:
            a = {}
            a['title'] = t.text_content()
            click = t.attrib['onclick']
            if click.find('to') == 0:
                a['conclusion'] = int(click.split(' ')[1][:-1])
            else:
                a['jump'] = int(click.split(' ')[1][:-1])
            ret.append(a)
        return ret

    def conclusion(self, d):
        '结论列表'
        self.conclusions = []
        for t in d:
            c = {}
            c['num'] = int(t.attrib['id'].split('_')[1])
            title = t.text
            c['title'] = title[2:]
            c['content'] = t.text_content()[len(title):]
            self.conclusions.append(c)

def main():
    tests = []
    for i in [
            'art44263.aspx', 'art44129.aspx', 'art44069.aspx', 'art43894.aspx', 'art43652.aspx',
            'art43444.aspx', 'art43425.aspx', 'art43256.aspx', 'art43234.aspx', 'art43180.aspx',
            'art43084.aspx', 'art43078.aspx', 'art42689.aspx', 'art42684.aspx', 'art41674.aspx',
            'art41565.aspx', 'art41314.aspx', 'art41311.aspx', 'art41278.aspx', 'art41125.aspx',
            'art41085.aspx', 'art41080.aspx', 'art41041.aspx', 'art41001.aspx', 'art40721.aspx',
            'art40698.aspx', 'art40621.aspx', 'art40587.aspx', 'art41652.aspx', 'art40305.aspx',
            'art43613.aspx',
            ]:
        t = Test(filename=i, num=int(i[3:-5]))
        log.info(t.num)
        tests.append(t.__dict__)
    with open('tests.json', 'w') as outfile:
        outfile.write(json.dumps(tests, ensure_ascii=False))

if __name__ == '__main__':
    log.info('开始')
    main()
    log.info('结束')

