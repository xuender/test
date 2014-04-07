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
        if Answer.objects.filter(title=answer['title'], question=question).count() == 0:
            a = Answer()
            a.question = question
            a.title = answer['title']
            if 'num' in answer:
                a.num = answer['num']
            if 'summary' in answer:
                a.summary = answer['summary']
            if 'content' in answer:
                a.content = answer['content']
            if 'jump' in answer:
                self.answers[a] = answer['jump']
                #a.jump = question.test.questions.filter(num = answer['jump'])[0]
            if 'conclusion' in answer:
                if question.test.conclusions.filter(num = answer['conclusion']).count()>0:
                    a.conclusion = question.test.conclusions.filter(num = answer['conclusion'])[0]
                else:
                    log.error(u'answer conclusion %s %d' %( a.title, answer['conclusion']))
            a.save()
            log.debug(u'保存答案 %s' % a.title)

    def readQuestion(self, question, test):
        '读取问题'
        if Question.objects.filter(num=question['num'], test=test).count() == 0:
            q = Question()
            q.test = test
            q.num = question['num']
            q.title = question['title']
            if 'summary' in question:
                q.summary = question['summary']
            if 'content' in question:
                q.content = question['content']
            q.save()
            log.debug(u'增加问题: %s' % q.title)
            for a in question['answers']:
                self.readAnswer(a, q)
    def readConclusion(self, conclusion, test):
        '读取结果'
        if Conclusion.objects.filter(num=conclusion['num'], test=test).count() == 0:
            c = Conclusion()
            c.test = test
            c.num = conclusion['num']
            c.title = conclusion['title']
            if 'summary' in conclusion:
                c.summary = conclusion['summary']
            c.content = conclusion['content']
            c.save()
            log.debug(u'增加结果: %s' % c.title)

    def readTest(self, test):
        '读取测试对象'
        if Test.objects.filter(num=test['num']).count() == 0:
            t = Test()
            t.num = test['num']
            t.title = test['title']
            if 'summary' in test:
                t.summary = test['summary']
            t.content = test['content']
            t.save()
            for tag in test['tags']:
                t.tags.add(Tag.add(tag))
            log.debug(u'增加测试: %s' % t.title)
        t = Test.objects.filter(num=test['num'])[0]
        for c in test['conclusions']:
            self.readConclusion(c, t)
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
