# -*- coding: UTF-8 -*-
import logging
log = logging.getLogger(__name__)
#import os
#from path import path
from django.core.management.base import BaseCommand
from itest.models import *
import json

def toDict(obj):
    t = {}
    if obj.title:
        t['title'] = obj.title.encode('utf-8')
    if obj.num:
        t['num'] = obj.num
    if obj.summary:
        t['summary'] = obj.summary.encode('utf-8')
    if obj.content:
        t['content'] = obj.content.encode('utf-8')
    return t


class Command(BaseCommand):
    '保存json文件'
    def readAnswer(self, answers):
        ret = []
        for c in answers:
            a = toDict(c)
            if c.jump:
                a['jump'] = c.jump.num
            if c.conclusion:
                a['conclusion'] = c.conclusion.num
            ret.append(a)
        return ret

    def readQuestion(self, questions):
        '结论'
        ret = []
        for c in questions:
            q = toDict(c)
            q['answers'] = self.readAnswer(c.answers.all())
            ret.append(q)
        return ret

    def readConclusion(self, conclusions):
        '结论'
        ret = []
        for c in conclusions:
            con = toDict(c)
            ret.append(con)
        return ret
    def save(self, filename):
        '保存文件'
        tests = []
        for test in Test.objects.all():
            t = toDict(test)
            t['type'] = test.type.encode('utf-8')
            t['star'] = test.star
            t['conclusions'] = self.readConclusion(test.conclusions.all())
            t['questions'] = self.readQuestion(test.questions.all())
            tests.append(t)
        with open(filename, 'w') as outfile:
            outfile.write(json.dumps(tests, ensure_ascii=False))

    def handle(self, *args, **options):
        log.debug('start')
        if len(args) == 0:
            self.save('../ar/itest/src/main/assets/tests.json')
            return
        f = args[0]
        log.debug('save: %s' % f)
        self.save(f)
        log.debug('end')
