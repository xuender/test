# -*- coding: UTF-8 -*-
import logging
log = logging.getLogger(__name__)
#import os
#from path import path
from django.core.management.base import BaseCommand
from itest.models import *
import json


class Command(BaseCommand):
    '读取json文件'
    answers = {}
    def readAnswer(self, answer, question):
        '读取答案'
        title = answer['title'].strip()
        a = Answer.objects.filter(title=title, question=question)[0]
        if 'jump' in answer:
            if question.test.questions.filter(num = answer['jump']).count()>0:
                a.jump = question.test.questions.filter(num = answer['jump'])[0]
            else:
                print(u'answer jump %s %d' %( a.title, answer['jump']))
        if 'conclusion' in answer:
            if question.test.conclusions.filter(num = answer['conclusion']).count()>0:
                a.conclusion = question.test.conclusions.filter(num = answer['conclusion'])[0]
            else:
                print(u'answer conclusion %s %d' %( a.title, answer['conclusion']))
        a.save()
        log.debug(u'保存答案 %s' % a.title)

    def readQuestion(self, question, test):
        '读取问题'
        q = Question.objects.filter(num=question['num'], test=test)[0]
        for a in question['answers']:
            self.readAnswer(a, q)

    def readTest(self, test):
        '读取测试对象'
        t = Test.objects.filter(num=test['num'])[0]
        for q in test['questions']:
            self.readQuestion(q, t)

    def read(self, fileName):
        '读取json文件'
        f = file(fileName);
        s = json.load(f)
        for t in s:
            self.readTest(t)

    def handle(self, *args, **options):
        log.debug('start')
        if len(args) == 0:
            log.info('缺少JSON文件，无法加载')
            return
        f = args[0]
        log.debug('read: %s' % f)
        self.read(f)
        for (a, num) in  self.answers.items():
            a.jump = a.question.test.questions.filter(num = num)[0]
            a.save()
            log.debug(u'修改jump %s'%a.title)
        log.debug('end')
